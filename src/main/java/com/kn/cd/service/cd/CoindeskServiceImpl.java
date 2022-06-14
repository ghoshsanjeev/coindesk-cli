package com.kn.cd.service.cd;

import com.kn.cd.exception.CoindeskException;
import com.kn.cd.model.BitCoinPriceIndex;
import com.kn.cd.model.CurrentPrice;
import com.kn.cd.model.HistoricalPrice;
import com.kn.cd.service.bpi.BPIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
public class CoindeskServiceImpl implements CoindeskService {

    private final Integer COUNT_HISTORICAL_DAYS = 30;
    private final DecimalFormat currencyFormat = new DecimalFormat("###,###.####");
    private final BPIService bpiService;

    public CoindeskServiceImpl(BPIService bpiService) {
        this.bpiService = bpiService;
    }

    @Value("${app.exception.message}")
    private String COINDESK_ERROR_MESSAGE;

    @Override
    public void run(String currency) {
        log.debug("Selected currency: " + currency);
        try {
            printCurrentPriceForCurrencyCode(currency);
            printHistoricalPriceForCurrencyCode(currency);
        } catch (CoindeskException ex) {
            log.error(ex.getMessage());
            System.err.println(ex.getMessage());
        }
    }

    public BitCoinPriceIndex getCurrentBPIForCurrencyCode(String currency) throws CoindeskException {
        CurrentPrice currentPrice = bpiService.getCurrentBPI(currency).orElseThrow(() -> new CoindeskException(COINDESK_ERROR_MESSAGE));
        return currentPrice.getBpi().get(currency.toUpperCase());
    }

    public Map<String, BigDecimal> getHistoricalBPIMapForCurrencyCode(String currency) throws CoindeskException {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(COUNT_HISTORICAL_DAYS);
        HistoricalPrice historyPrice = bpiService.getHistoricalBPI(startDate, endDate, currency).orElseThrow(() -> new CoindeskException(COINDESK_ERROR_MESSAGE));
        return historyPrice.getBpi();
    }

    private void printCurrentPriceForCurrencyCode(String currency) throws CoindeskException {
        BitCoinPriceIndex bpi = getCurrentBPIForCurrencyCode(currency);
        System.out.printf("Current BitCoin Rate in [%s]= %s%n", currency.toUpperCase(), bpi.getRate());
    }


    private void printHistoricalPriceForCurrencyCode(String currency) throws CoindeskException {

        Map<String, BigDecimal> bpiMap = getHistoricalBPIMapForCurrencyCode(currency);
        BigDecimal highestPrice = Collections.max(bpiMap.values());
        BigDecimal lowestPrice = Collections.min(bpiMap.values());

        System.out.printf("""
                In last %d days:
                    Bitcoin rate in [%s]
                    Lowest  = %s
                    Highest = %s
                %n""", COUNT_HISTORICAL_DAYS, currency.toUpperCase(), currencyFormat.format(lowestPrice), currencyFormat.format(highestPrice));
    }

}
