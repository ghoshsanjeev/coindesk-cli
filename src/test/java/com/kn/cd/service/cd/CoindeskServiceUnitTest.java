package com.kn.cd.service.cd;

import com.kn.cd.component.JavaScriptMessageConverter;
import com.kn.cd.component.RestTemplateResponseErrorHandler;
import com.kn.cd.model.CoindeskStat;
import com.kn.cd.service.bpi.BPIService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Sanjeev on 03-07-2022
 * @Project: coindesk-cli
 */

@SpringBootTest(classes = {PrintService.class, CoindeskService.class, BPIService.class, JavaScriptMessageConverter.class, RestTemplateResponseErrorHandler.class, RestTemplate.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoindeskServiceUnitTest {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JavaScriptMessageConverter javaScriptMessageConverter;

    @Mock
    private BPIService bpiService;

    @Mock
    PrintService printService;

    @InjectMocks
    @Autowired
    private CoindeskService coindeskService;

    @BeforeAll
    void setUp() {
        this.restTemplate.getMessageConverters().add(javaScriptMessageConverter);
        /*bpiService = new BPIService(restTemplate);
        coindeskService = new CoindeskServiceImpl(bpiService, coindeskPrint);*/
    }

    @Test
    void testCoindeskService() throws CoindeskException {
        String currency = "INR";

        CoindeskStat stat = coindeskService.getPriceStat(currency, true);

        assertThat(Objects.nonNull(stat));
        assertThat(stat.getHighestPrice().compareTo(stat.getLowestPrice()) > 0);

        /*doNothing().when(coindeskPrint).print(stat);
        verify(coindeskPrint, times(1)).print(stat);*/

    }


    @Test
    void testCoinDeskException() {
        String currency = "MOON";

        assertThrows(CoindeskException.class, () -> coindeskService.getPriceStat(currency, false));

    }


}