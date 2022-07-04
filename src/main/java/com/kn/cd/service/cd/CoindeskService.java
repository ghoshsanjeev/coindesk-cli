package com.kn.cd.service.cd;

import com.kn.cd.model.BitCoinPriceIndex;
import com.kn.cd.model.CoindeskStat;
import com.kn.cd.model.CurrentPrice;
import com.kn.cd.model.HistoricalPrice;
import com.kn.cd.service.bpi.BPIService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Service
public class CoindeskService {

    private final Integer COUNT_HISTORICAL_DAYS = 30;

    private final BPIService bpiService;
    private final PrintService printService;

    @Autowired
    public CoindeskService(BPIService bpiService, PrintService printService) {
        this.bpiService = bpiService;
        this.printService = printService;
    }

    @Value("${app.exception.message}")
    private String COINDESK_ERROR_MESSAGE;

    public CoindeskStat getPriceStat(String currency, boolean shouldPrint) throws CoindeskException {
        long startTimeMs = System.currentTimeMillis();
        log.debug("Selected currency: " + currency);
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(COUNT_HISTORICAL_DAYS);

        CoindeskStat stat = new CoindeskStat(COUNT_HISTORICAL_DAYS, currency);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<CoindeskStat> currentPriceTask = executor.submit(new CurrentPriceTask(stat));
        Future<CoindeskStat> historicalPriceTask = executor.submit(new HistoricalPriceTask(stat, startDate, endDate));

        while (!currentPriceTask.isDone() || !historicalPriceTask.isDone()) {
            //waiting
        }

        try {
            currentPriceTask.get();
            historicalPriceTask.get();
        } catch (InterruptedException e) {
            throw new CoindeskException(e.getCause().getMessage());
        } catch (ExecutionException e) {
            throw new CoindeskException(e.getCause().getMessage());
        } finally {
            executor.shutdown();
        }

        if (shouldPrint) {
            printService.print(stat);
        }

        long endTimeMs = System.currentTimeMillis();
        log.debug("Total fetch time: " + (endTimeMs - startTimeMs) + "ms");
        return stat;
    }

    @RequiredArgsConstructor
    private class CurrentPriceTask implements Callable<CoindeskStat> {
        @NonNull
        private CoindeskStat stat;

        @Override
        public CoindeskStat call() throws Exception {
            BitCoinPriceIndex currentBpi = getCurrentBPIForCurrencyCode(stat.getCurrency());
            stat.setCurrentRate(currentBpi.getRate());
            return stat;
        }
    }

    @RequiredArgsConstructor
    private class HistoricalPriceTask implements Callable<CoindeskStat> {

        @NonNull
        private CoindeskStat stat;
        @NonNull
        private LocalDate startDate;
        @NonNull
        private LocalDate endDate;

        @Override
        public CoindeskStat call() throws Exception {

            Map<LocalDate, BigDecimal> historicalBpiMap = getHistoricalBPIMapForCurrencyCode(startDate, endDate, stat.getCurrency());
            stat.setHighestPrice(getHistoricalHighestPrice(historicalBpiMap));
            stat.setLowestPrice(getHistoricalLowestPrice(historicalBpiMap));
            return stat;
        }
    }

    private BitCoinPriceIndex getCurrentBPIForCurrencyCode(String currency) throws CoindeskException {
        CurrentPrice currentPrice = bpiService.getCurrentBPI(currency).orElseThrow(() -> new CoindeskException(COINDESK_ERROR_MESSAGE));
        return currentPrice.getBpi().get(currency.toUpperCase());
    }

    private Map<LocalDate, BigDecimal> getHistoricalBPIMapForCurrencyCode(LocalDate startDate, LocalDate endDate, String currency) throws CoindeskException {
        HistoricalPrice historyPrice = bpiService.getHistoricalBPI(startDate, endDate, currency).orElseThrow(() -> new CoindeskException(COINDESK_ERROR_MESSAGE));
        return historyPrice.getBpi();
    }

    private BigDecimal getHistoricalHighestPrice(Map<LocalDate, BigDecimal> historicalBpi) {
        return Collections.max(historicalBpi.values());
    }

    private BigDecimal getHistoricalLowestPrice(Map<LocalDate, BigDecimal> historicalBpi) {
        return Collections.min(historicalBpi.values());
    }

}
