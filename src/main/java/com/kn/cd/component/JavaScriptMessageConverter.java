package com.kn.cd.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

/**
 * When receiving a new request, Spring will use the “Accept” header to determine the media type that it needs to respond with.
 *
 * It will then try to find a registered converter that's capable of handling that specific media type.
 * Finally, it will use this to convert the entity and send back the response.
 *
 * @Usage: The bean is used to be added to the message converters of the restTemplate bean, declared in the AppConfig class
 */
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
