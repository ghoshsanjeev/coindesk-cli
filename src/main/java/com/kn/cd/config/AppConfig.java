package com.kn.cd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Autowired
    private JavaScriptMessageConverter javaScriptMessageConverter;

    @Autowired
    private RestTemplateResponseErrorHandler errorHandler;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        RestTemplate restTemplate = builder
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
        restTemplate.getMessageConverters().add(javaScriptMessageConverter);
        restTemplate.setErrorHandler(errorHandler);

        return restTemplate;
    }

}
