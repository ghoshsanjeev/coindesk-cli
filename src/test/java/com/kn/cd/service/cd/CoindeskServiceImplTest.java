package com.kn.cd.service.cd;

import com.kn.cd.model.BitCoinPriceIndex;
import com.kn.cd.model.CurrentPrice;
import com.kn.cd.service.bpi.BPIService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoindeskServiceImplTest {

    @Mock
    private BPIService bpiService;

    private CoindeskServiceImpl coindeskService;

    @BeforeAll
    public void setUp() {
        coindeskService = new CoindeskServiceImpl(bpiService);
    }

    @Test
    void getCurrentBPI() throws CoindeskException {
        CurrentPrice currentPrice = new CurrentPrice();

        String currency = "USD";
        Map<String, BitCoinPriceIndex> bpiMap = new HashMap<>();
        BitCoinPriceIndex bpiGiven = new BitCoinPriceIndex();
        bpiGiven.setCode("USD");
        bpiGiven.setDescription("United States Dollar");
        bpiGiven.setRate("22,397.5376");
        bpiGiven.setRateFloat(BigDecimal.valueOf(22397.5376));
        bpiMap.put("USD", bpiGiven);

        currentPrice.setBpi(bpiMap);
        given(bpiService.getCurrentBPI("USD")).willReturn(Optional.of(currentPrice));

        BitCoinPriceIndex bpiGot = coindeskService.getCurrentBPIForCurrencyCode(currency);
        assertThat(bpiGot).isEqualTo(bpiGiven);
    }

}