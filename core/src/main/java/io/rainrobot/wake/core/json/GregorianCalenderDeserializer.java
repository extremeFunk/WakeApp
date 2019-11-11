package io.rainrobot.wake.core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.GregorianCalendar;

public class GregorianCalenderDeserializer extends JsonDeserializer<GregorianCalendar> {
	@Override
	public GregorianCalendar deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		GregorianCalendar c = new GregorianCalendar();
		c.setTimeInMillis(p.getLongValue());
		return c;
	}
}
