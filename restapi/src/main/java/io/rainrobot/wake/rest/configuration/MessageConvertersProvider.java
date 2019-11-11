package io.rainrobot.wake.rest.configuration;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.rainrobot.wake.core.Account;
import io.rainrobot.wake.core.json.AccountDesrializer;
import io.rainrobot.wake.core.json.AccountSerializer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class MessageConvertersProvider {

    private static SimpleModule getJsonModule() {
        SimpleModule module = new SimpleModule();

        module.addSerializer(Account.class, new AccountSerializer());
        module.addDeserializer(Account.class, new AccountDesrializer());
        return module;
    }

    public static HttpMessageConverter<?> get() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.getObjectMapper().registerModules(getJsonModule());
        return converter;
    }
}
