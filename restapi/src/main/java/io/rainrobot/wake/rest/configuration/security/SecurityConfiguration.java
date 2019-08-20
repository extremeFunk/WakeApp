package io.rainrobot.wake.rest.configuration.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.rainrobot.wake.rest.configuration.appuser.service.UserService;
import io.rainrobot.wake.rest.configuration.security.filter.JwtAuthenticationFilter;
import io.rainrobot.wake.rest.configuration.security.filter.JwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private JwtAuthenticationFilter createAuthenticateFilter() throws Exception {
		return new JwtAuthenticationFilter(super.authenticationManager());
	}

	private JwtAuthorizationFilter createAuthorizationFilter() throws Exception {
		return new JwtAuthorizationFilter(	super.authenticationManager(), 
											userService,
											getPermitAllUriList());
	}
 
 	private Set<String> getPermitAllUriList() {
 		Set<String> permitAllUriList = new HashSet<>();
		permitAllUriList.add("singup");
		permitAllUriList.add("login");
		permitAllUriList.add("/reset_password");
		
		return permitAllUriList;
	}

	@Override
 	protected void configure(HttpSecurity http) throws Exception {
 		 http	.cors().and()
 		 		.csrf().disable()
 		 		.addFilter(createAuthenticateFilter())
 		 		.addFilter(createAuthorizationFilter())
 		 		.authorizeRequests()
 		 		.antMatchers("/login", "/singup", "/reset_password")
				 .permitAll();
 	}
}
	

