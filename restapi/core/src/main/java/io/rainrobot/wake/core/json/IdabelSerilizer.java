package io.rainrobot.wake.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import io.rainrobot.wake.core.Idabel;


public class IdabelSerilizer <T extends Idabel>extends JsonSerializer<T> {
    public static final String LABEL = "id";

    @Override
    public void serialize(T value,
                          JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(LABEL, value.getId());
        gen.writeEndObject();
    }
}

