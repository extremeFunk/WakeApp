package io.rainrobot.wake.rest.configuration;

import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.GregorianCalendar;

import io.rainrobot.wake.rest.dto.Account;
import io.rainrobot.wake.rest.dto.AccountDesrializer;
import io.rainrobot.wake.rest.dto.AccountSerializer;
import io.rainrobot.wake.rest.dto.GregorianCalenderDeserializer;
import io.rainrobot.wake.rest.dto.GregorianCalenderSerializer;

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
