package io.rainrobot.wake.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;


public class AccountSerializer extends JsonSerializer<Account> {

    public static final String ID = "id";
    public static final String USERNAME = "username";

    @Override
    public void serialize(Account account, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(ID, account.getId());
        gen.writeStringField(USERNAME, account.getUsername());
        gen.writeEndObject();
    }
}
