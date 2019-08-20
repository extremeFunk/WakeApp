package io.rainrobot.wake.rest;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import io.rainrobot.wake.rest.configuration.MessageConvertersProvider;
import io.rainrobot.wake.rest.configuration.security.EnumToStringConverter;
import io.rainrobot.wake.rest.configuration.security.StringToStateConverter;

@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

	@Override
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(MessageConvertersProvider.get());
		super.extendMessageConverters(converters);
	}

	@Override
	public FormattingConversionService mvcConversionService() {
	   FormattingConversionService f = super.mvcConversionService();
	   f.addConverter(new EnumToStringConverter());
	   f.addConverter(new StringToStateConverter());
	   return f;
	}
}