package io.rainrobot.wake.rest.request.singup;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.rainrobot.wake.rest.dto.AppUser;
import io.rainrobot.wake.rest.configuration.appuser.Authority;
import io.rainrobot.wake.rest.configuration.appuser.State;
import io.rainrobot.wake.rest.dto.SignUpForm;

import java.util.ArrayList;
import java.util.List;

public class AppUserBuilder {

	public static AppUser build(SignUpForm form, PasswordEncoder encoder) {
		
		return AppUser.builder().username(form.getUsername())
				.password(encoder.encode(form.getPassword()))
				.authority(getUserAuthority())
				.email(form.getEmail())
				.state(State.ACTIVE).build();
	}

	private static List<GrantedAuthority> getUserAuthority() {
		List<GrantedAuthority> list = new ArrayList();
		list.add(() -> String.valueOf(Authority.ROLE_USER));
		return list;
	}

}
