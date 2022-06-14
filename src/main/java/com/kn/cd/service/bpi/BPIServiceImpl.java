package com.kn.cd.service.bpi;

import com.kn.cd.model.CurrentPrice;
import com.kn.cd.model.HistoricalPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class BPIServiceImpl implements BPIService {

    private final String URI_CURRENT_PRICE = "https://api.coindesk.com/v1/bpi/currentprice/%s.json";
    private final String URI_HISTORICAL_PRICE = "https://api.coindesk.com/v1/bpi/historical/close.json?start=%s&end=%s&currency=%s";

    private final RestTemplate restTemplate;

    public BPIServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<CurrentPrice> getCurrentBPI(String currency) {
        log.debug("Fetching current BPI for currency: " + currency);
        CurrentPrice currentPrice = null;
        try {
            currentPrice = restTemplate.getForObject(URI_CURRENT_PRICE.formatted(currency), CurrentPrice.class);
            log.debug("Current Price: " + currentPrice);
        } catch (RestClientException e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(currentPrice);

    }

    public Optional<HistoricalPrice> getHistoricalBPI(LocalDate startDate, LocalDate endDate, String currency) {
        //log.debug("Getting historical BPI for start-date: %s, end-date: %s, currency: %s".formatted(startDate, endDate, currency));
        HistoricalPrice historyPrice = null;
        try {
            historyPrice = restTemplate.getForObject(URI_HISTORICAL_PRICE.formatted(startDate, endDate, currency), HistoricalPrice.class);
            log.debug("Historical Price: " + historyPrice);
        } catch (RestClientException e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(historyPrice);
    }
}
