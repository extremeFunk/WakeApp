package io.rainrobot.wake.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
@Deprecated
public class PresetDesrializer extends JsonDeserializer<Preset> {

    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    public Preset deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        int id = (Integer)node.get(ID).numberValue();
        String name = node.get(NAME).asText();

        Preset preset = new Preset();
        preset.setId(id);
        preset.setName(name);
        return preset;
    }
}
