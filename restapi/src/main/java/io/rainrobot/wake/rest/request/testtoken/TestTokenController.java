package io.rainrobot.wake.rest.request.testtoken;

import io.rainrobot.wake.core.Path;
import io.rainrobot.wake.rest.configuration.appuser.AppAuth;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class TestTokenController {
	@RequestMapping(method = RequestMethod.GET, value = Path.TESTTOKEN + "/{userNm}")
	public void testToken(@AuthenticationPrincipal AppAuth auth, @PathVariable String userNm) {
		if (!check(auth, userNm)) throw new InvalidTokenException("token dosn't match username");
	}

	public boolean check(@AuthenticationPrincipal AppAuth auth, String userNm) {
		return auth.isAuthenticated() && auth.getName().equals(userNm);
	}

	private class InvalidTokenException extends IllegalArgumentException {
		public InvalidTokenException(String msg) {
			super(msg);
		}
	}
}
