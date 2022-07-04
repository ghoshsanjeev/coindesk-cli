package com.kn.cd.model;

import lombok.*;

import java.math.BigDecimal;

/**
 * @author Sanjeev on 03-07-2022
 * @Project: coindesk-cli
 */

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class CoindeskStat {
    @NonNull
    private int days;
    @NonNull
    private String currency;
    private BigDecimal lowestPrice;
    private BigDecimal highestPrice;
    private String currentRate;
}
