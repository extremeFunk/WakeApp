package io.rainrobot.wake.rest.request.singup;

import org.springframework.security.crypto.password.PasswordEncoder;

import io.rainrobot.wake.rest.dto.AppUser;
import io.rainrobot.wake.rest.configuration.appuser.Authority;
import io.rainrobot.wake.rest.configuration.appuser.State;
import io.rainrobot.wake.core.SignUpForm;

public class AppUserBuilder {

	public static AppUser build(SignUpForm form, PasswordEncoder encoder) {
		
		AppUser user = new AppUser();
		
		user.setUsername(form.getUsername());
		
		user.setPassword(encoder.encode(form.getPassword()));
		
		user.setAuthority(Authority.USER);

		user.setEmail(form.getEmail());
		
		user.setState(State.ACTIVE);
		
		return user;
	}

}
