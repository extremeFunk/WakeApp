package io.rainrobot.wake.core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;

import java.io.IOException;

import io.rainrobot.wake.core.Idabel;

import static io.rainrobot.wake.core.json.IdabelSerilizer.LABEL;


public abstract class IdabelDeSerializer<T extends Idabel> extends JsonDeserializer<T> {

    protected abstract T getInstance();

    @Override
    public T deserialize(JsonParser p, DeserializationContext context)
                        throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        Integer id = (Integer) ((IntNode) node.get(LABEL)).numberValue();
        T idebel = getInstance();
        idebel.setId(id);
        return idebel;
    }
}
