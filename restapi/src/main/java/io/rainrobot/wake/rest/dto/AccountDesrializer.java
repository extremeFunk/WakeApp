package io.rainrobot.wake.rest.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;

import java.io.IOException;

@Deprecated
public class AccountDesrializer extends JsonDeserializer<Account> {

	@Override
    public Account deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        int id = (Integer)((IntNode)node.get(AccountSerializer.ID)).numberValue();
        String username = node.get(AccountSerializer.USERNAME).asText();

        Account account = null;
        // = new Account(id, username);
        return account;

	}
}
