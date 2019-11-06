package io.rainrobot.wake.rest.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.GregorianCalendar;

public class GregorianCalenderSerializer extends JsonSerializer<GregorianCalendar> {

	@Override
	public void serialize(GregorianCalendar value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeNumber(value.getTimeInMillis());
	}
}
