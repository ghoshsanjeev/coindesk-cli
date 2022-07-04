package com.kn.cd.service.bpi;

import com.kn.cd.component.JavaScriptMessageConverter;
import com.kn.cd.component.RestTemplateResponseErrorHandler;
import com.kn.cd.model.CurrentPrice;
import com.kn.cd.model.HistoricalPrice;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sanjeev on 03-07-2022
 * @Project: coindesk-cli
 */

@SpringBootTest(classes = {BPIService.class, JavaScriptMessageConverter.class, RestTemplateResponseErrorHandler.class, RestTemplate.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BPIServiceUnitTest {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JavaScriptMessageConverter javaScriptMessageConverter;

    private BPIService bpiService;

    @BeforeAll
    void setUp() {
        this.bpiService = new BPIService(restTemplate);
        restTemplate.getMessageConverters().add(javaScriptMessageConverter);
    }

    @Test
    void testCurrentBPI() {
        String currency = "INR";
        Optional<CurrentPrice> optionalPrice = bpiService.getCurrentBPI(currency);
        assertThat(optionalPrice.isPresent());
        assertThat(optionalPrice.get().getBpi().get("INR").getRateFloat().compareTo(BigDecimal.valueOf(0)) > 0);
    }

    @Test
    void testHistoricalBPI() {
        String currency = "INR";
        LocalDate endDate = LocalDate.now();
        Optional<HistoricalPrice> optionalPrice = bpiService.getHistoricalBPI(LocalDate.now().minusDays(30), endDate, currency);
        assertThat(optionalPrice.isPresent());
        assertThat(optionalPrice.get().getBpi().size() > 0);
    }


    @Test
    void testCurrentBPiUnsupportedCurrency() {
        String currency = "XYZ";
        Optional<CurrentPrice> optionalPrice = bpiService.getCurrentBPI(currency);
        assertThat(optionalPrice.isEmpty());
    }
}