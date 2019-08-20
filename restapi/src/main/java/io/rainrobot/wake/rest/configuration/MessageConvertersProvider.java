package io.rainrobot.wake.rest.configuration;

import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.GregorianCalendar;

import io.rainrobot.wake.core.Account;
import io.rainrobot.wake.core.AccountDesrializer;
import io.rainrobot.wake.core.AccountSerializer;
import io.rainrobot.wake.core.GregorianCalenderDeserializer;
import io.rainrobot.wake.core.GregorianCalenderSerializer;

public class MessageConvertersProvider {

    private static SimpleModule getJsonModule() {
        SimpleModule module = new SimpleModule();

        module.addSerializer(Account.class, new AccountSerializer());
        module.addDeserializer(Account.class, new AccountDesrializer());
        module.addSerializer(GregorianCalendar.class, new GregorianCalenderSerializer());
        module.addDeserializer(GregorianCalendar.class, new GregorianCalenderDeserializer());
        return module;
    }

    public static HttpMessageConverter<?> get() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.getObjectMapper().registerModules(getJsonModule());
        return converter;
    }
}
