package com.kn.cd.service.cd;

import com.kn.cd.model.*;
import com.kn.cd.service.bpi.BPIService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * @author Sanjeev on 03-07-2022
 * @Project: coindesk-cli
 */
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoindeskServiceBDDTest {
    @Mock
    private BPIService bpiService;

    @InjectMocks
    private CoindeskService coindeskService;

    @Test
    void testBehaviorOfCoindeskService() throws CoindeskException {
        // Setup current price
        CurrentPrice currentPrice = new CurrentPrice();

        String currency = "XYZ";
        Map<String, BitCoinPriceIndex> bpiMap = new HashMap<>();
        BitCoinPriceIndex bpiCurrentGiven = new BitCoinPriceIndex();
        bpiCurrentGiven.setCode(currency);
        bpiCurrentGiven.setDescription("United States Dollar");
        bpiCurrentGiven.setRate("29,500.7654");
        bpiCurrentGiven.setRateFloat(BigDecimal.valueOf(29500.7654));
        bpiMap.put(currency, bpiCurrentGiven);
        currentPrice.setBpi(bpiMap);

        given(bpiService.getCurrentBPI(currency)).willReturn(Optional.of(currentPrice));

        // Setup historical price
        HistoricalPrice historyPrice = new HistoricalPrice();
        LocalDate today = LocalDate.now();

        Map<LocalDate, BigDecimal> bpiHistoryGiven = new HashMap<>(5);
        bpiHistoryGiven.put(today.minusDays(30), BigDecimal.valueOf(29378.75));
        bpiHistoryGiven.put(today.minusDays(3), BigDecimal.valueOf(29379.75));
        bpiHistoryGiven.put(today.minusDays(2), BigDecimal.valueOf(29380.75));
        bpiHistoryGiven.put(today.minusDays(1), BigDecimal.valueOf(29420.75));
        bpiHistoryGiven.put(today, BigDecimal.valueOf(29500.7654));
        historyPrice.setDisclaimer("This data was produced from the CoinDesk Bitcoin Price Index. BPI value data returned as USD.");
        Time time = new Time();
        historyPrice.setTime(time);
        time.setUpdated("Jun 15, 2022 00:03:00 UTC");
        time.setUpdatedISO("2022-06-15T00:03:00+00:00");
        historyPrice.setBpi(bpiHistoryGiven);

        given(bpiService.getHistoricalBPI(today.minusDays(30), today, currency))
                .willReturn(Optional.of(historyPrice));

        CoindeskStat expectedStat = new CoindeskStat(30, currency);
        expectedStat.setCurrentRate(bpiCurrentGiven.getRate());
        expectedStat.setLowestPrice(BigDecimal.valueOf(29378.75));
        expectedStat.setHighestPrice(BigDecimal.valueOf(29500.7654));

        CoindeskStat stat = coindeskService.getPriceStat(currency, false);

        assertEquals(expectedStat, stat);

    }




}