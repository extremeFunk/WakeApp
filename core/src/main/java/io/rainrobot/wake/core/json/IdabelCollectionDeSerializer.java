package io.rainrobot.wake.core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import io.rainrobot.wake.core.Idabel;

public abstract class IdabelCollectionDeSerializer <T extends Idabel>
                                        extends JsonDeserializer<Collection<T>> {
    @Override
    public Collection<T> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode tree = p.getCodec().readTree(p);
        List<JsonNode> nodeList = tree.findValues(IdabelSerilizer.LABEL);
        Collection<T> pojoList = getCollection();
        nodeList.forEach(n -> {
            T t = getInstance();
            t.setId(n.asInt());
            pojoList.add(t);
        });
        return pojoList;
    }

    protected abstract T getInstance();

    protected abstract Collection<T> getCollection();


}
