package io.rainrobot.wake.rest.configuration.security;

import org.springframework.core.convert.converter.Converter;

import io.rainrobot.wake.rest.configuration.appuser.State;

public class StringToStateConverter implements Converter<String, State> {
	
	@Override
	public State convert(String source) {
		
		try {
			
			return State.valueOf(source);
	      
	  } catch(Exception e) {
	        
		  	return null;
	      
	  }
		  
	}
	
}