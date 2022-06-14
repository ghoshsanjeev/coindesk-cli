package com.kn.cd.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitCoinPriceIndex {
    private String code;
    private String rate; //TODO: json format of the double value 4 decimal values, comma separated
    private String description;
    @JsonProperty("rate_float")
    private BigDecimal rateFloat;
}
