package io.rainrobot.wake.rest.configuration.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.rainrobot.wake.rest.dto.LoginEntity;
import io.rainrobot.wake.rest.configuration.Values;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authMgr;

	public JwtAuthenticationFilter(AuthenticationManager authMgr) {
		this.authMgr =	authMgr;
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			ServletInputStream stream = request.getInputStream();
			LoginEntity user = new ObjectMapper().readValue(
													stream,
													LoginEntity.class);
			UsernamePasswordAuthenticationToken token;
			token = new UsernamePasswordAuthenticationToken(
												user.getUsername(),
												user.getPassword());
			return authMgr.authenticate(token);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((User)authResult.getPrincipal()).getUsername();
		String token = TokenBuilder.create(username);
		response.getWriter().write(Values.TOKEN_PREFIX + token);
		response.addHeader(Values.AUTH_HEADER, Values.TOKEN_PREFIX + token);
	}
}
