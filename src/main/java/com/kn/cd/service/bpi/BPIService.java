package com.kn.cd.service.bpi;

import com.kn.cd.model.CurrentPrice;
import com.kn.cd.model.HistoricalPrice;

import java.time.LocalDate;
import java.util.Optional;

public interface BPIService {
    Optional<CurrentPrice> getCurrentBPI(String currency);
    Optional<HistoricalPrice> getHistoricalBPI(LocalDate startDate, LocalDate endDate, String currency);
}
