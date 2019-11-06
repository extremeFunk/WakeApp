package io.rainrobot.wake.rest.configuration.appuser;

import java.util.Collection;

import io.rainrobot.wake.rest.dto.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AppAuth implements Authentication{
	
	private AppUser user;
	
	private boolean isAuth;
	
	public AppAuth(AppUser appUser, boolean isAuth) {
		this.user = appUser;
		this.isAuth = isAuth;
	}

	public AppAuth(boolean isAuth) {
		this.isAuth = isAuth;
	}

	@Override
	public String getName() {
		return user.getUsername();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getAuthority();
	}

	@Override
	public Object getCredentials() {
		return user.getPassword();
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuth;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		isAuth = isAuthenticated;
	}
	
}
