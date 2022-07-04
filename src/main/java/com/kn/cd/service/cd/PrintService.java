package com.kn.cd.service.cd;

import com.kn.cd.model.CoindeskStat;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

/**
 * @author Sanjeev on 03-07-2022
 * @Project: coindesk-cli
 */

@Service
public class PrintService {

    private final DecimalFormat currencyFormat = new DecimalFormat("###,###.####");

    public void print(CoindeskStat stat) {
        System.out.printf("Current BitCoin Rate in [%s]= %s%n", stat.getCurrency().toUpperCase(), stat.getCurrentRate());
        System.out.printf("""
                        In last %d days:
                            Bitcoin rate in [%s]
                            Lowest  = %s
                            Highest = %s
                        %n""", stat.getDays(), stat.getCurrency().toUpperCase(),
                currencyFormat.format(stat.getLowestPrice()), currencyFormat.format(stat.getHighestPrice()));
    }
}
