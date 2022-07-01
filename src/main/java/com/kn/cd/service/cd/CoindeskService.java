package com.kn.cd.service.cd;

import com.kn.cd.model.BitCoinPriceIndex;

import java.math.BigDecimal;
import java.util.Map;

public interface CoindeskService {
    void run(String currency);
    BitCoinPriceIndex getCurrentBPIForCurrencyCode(String currency) throws CoindeskException;
    Map<String, BigDecimal> getHistoricalBPIMapForCurrencyCode(String currency) throws CoindeskException;
}
