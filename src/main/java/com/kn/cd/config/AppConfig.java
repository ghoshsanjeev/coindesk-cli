package com.kn.cd.config;

import com.kn.cd.component.JavaScriptMessageConverter;
import com.kn.cd.component.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.rest.connection.timeout:3000}")
    private Integer connectionTimeout;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        RestTemplate restTemplate = builder
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(connectionTimeout))
                .build();
        restTemplate.getMessageConverters().add(javaScriptMessageConverter);
        restTemplate.setErrorHandler(errorHandler);

        return restTemplate;
    }

}
