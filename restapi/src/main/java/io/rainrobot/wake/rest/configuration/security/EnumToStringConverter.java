package io.rainrobot.wake.rest.configuration.security;

import org.springframework.core.convert.converter.Converter;

import io.rainrobot.wake.rest.configuration.appuser.State;

public class EnumToStringConverter  implements Converter<State, String> {

	@Override
	public String convert(State source) {

		try {
			
			return source.name();
	      
	  } catch(Exception e) {
	        
		  	return null;
	      
	  }
		  
	}
	
}