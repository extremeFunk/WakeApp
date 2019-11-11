package io.rainrobot.wake.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Collection;

import io.rainrobot.wake.core.Idabel;

import static io.rainrobot.wake.core.json.IdabelSerilizer.LABEL;

public class IdabelCollectionSerializer <T extends Idabel>
                                                extends JsonSerializer<Collection<T>> {

    @Override
    public void serialize(Collection<T> values, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (Idabel val : values) {
            gen.writeStartObject();
            gen.writeNumberField(LABEL, val.getId());
            gen.writeEndObject();
        }
        gen.writeEndArray();
    }
}
