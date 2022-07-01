package com.kn.cd.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

@Component
public class JavaScriptMessageConverter extends AbstractJackson2HttpMessageConverter {

    //This is package private
    JavaScriptMessageConverter() {
        super(Jackson2ObjectMapperBuilder.json().build(),new MediaType("application","javascript"));
    }

    protected JavaScriptMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    protected JavaScriptMessageConverter(ObjectMapper objectMapper, MediaType supportedMediaType) {
        super(objectMapper, supportedMediaType);
    }

    protected JavaScriptMessageConverter(ObjectMapper objectMapper, MediaType... supportedMediaTypes) {
        super(objectMapper, supportedMediaTypes);
    }
}
