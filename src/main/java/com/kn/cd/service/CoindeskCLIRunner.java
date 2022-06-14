package com.kn.cd.service;

import com.kn.cd.service.cd.CoindeskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Profile("!test")
@Slf4j
@Component
public class CoindeskCLIRunner implements CommandLineRunner {

    @Autowired
    private CoindeskService coindeskService;

    @Override
    public void run(String... args) {
        String currency;
        Scanner scanner = new Scanner(System.in);

        log.debug("App started with " + args.length + " parameters.");
        if (args.length != 0) {
            currency = args[0];
        } else {
            System.out.print("Enter a currency code: [e.g. USD, EUR, GBP]: ");
            currency = scanner.next();
            scanner.close();
        }
        coindeskService.run(currency);
    }
}
