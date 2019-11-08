package io.rainrobot.wake.rest.configuration;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class MessageConvertersProvider {

    public static HttpMessageConverter<?> get() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        return converter;
    }
}
