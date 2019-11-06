package io.rainrobot.wake.rest.configuration.security.filter;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.rainrobot.wake.rest.configuration.Values;
import io.rainrobot.wake.rest.configuration.appuser.AppAuth;
import io.rainrobot.wake.rest.dto.AppUser;
import io.rainrobot.wake.rest.configuration.appuser.service.UserService;
import io.rainrobot.wake.rest.configuration.security.SecurityConstants;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	
	@Autowired
	private UserService userService;
	
	private Set<String> permitAllUriList;
	
	
	public JwtAuthorizationFilter(	AuthenticationManager authenticationManager, 
									UserService userService,
									Set<String> permitAllUriList) {
		super(authenticationManager);
		this.userService = userService;
		this.permitAllUriList = permitAllUriList;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
										HttpServletResponse response, 
										FilterChain chain) throws 	IOException, 
																	ServletException {
		if(checkIfPremitAllUri(request, response, chain)) {
			chain.doFilter(request, response);
			return;
		}
		
		String jwtToken = request.getHeader(Values.AUTH_HEADER);
		
		//bad request
		if(jwtToken == null) {
			response.sendError(400, "Authentication token is missing");
			return;
		}
		AppAuth auth;
		try {
			auth = createAuth(jwtToken);
		} catch (Exception e) {
			response.sendError(400, "Bad authentication token");
			return;
		}
		//OK request
		if (auth.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(auth);
			chain.doFilter(request, response);
		}
		//forbidden request
		else {
			response.sendError(403);
			return;
		}
	}

	private boolean checkIfPremitAllUri(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
		for(String s : permitAllUriList) {
			if (request.getRequestURL().toString().contains(s)) {
				return true;
			}
		}
		return false;
	}
	
	private String getUsernameFormToken(String jwtToken) {
		return Jwts.parser().setSigningKey(SecurityConstants.SECRET)
				.parseClaimsJws(jwtToken.replace(Values.TOKEN_PREFIX, ""))
				.getBody()
				.getSubject();
	}

	private AppAuth createAuth(String jwtToken) {
		String username = getUsernameFormToken(jwtToken);
		AppUser appUser = userService.findByUsername(username);
		
		if (appUser != null) 	return new AppAuth(appUser, true);
		//return an unauthorized authorization
		else return new AppAuth(false);
	}
}