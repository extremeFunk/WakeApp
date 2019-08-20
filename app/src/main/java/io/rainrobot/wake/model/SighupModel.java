package io.rainrobot.wake.model;

import java.util.Arrays;

import io.rainrobot.wake.app.IModel;
import io.rainrobot.wake.app.DeviceInfoMgr;
import io.rainrobot.wake.app.LoginMgr;
import io.rainrobot.wake.client.SignupClient;
import io.rainrobot.wake.core.SignUpForm;
import io.rainrobot.wake.core.SingupError;

public class SighupModel implements IModel {

	private SignupClient signupClient;
	private LoginMgr logInMgr;
	private DeviceInfoMgr deviceInfoMgr;

	private String username;
	private String password;

	public SighupModel(SignupClient signupClient,
					   LoginMgr logInMgr,
					   DeviceInfoMgr deviceInfoMgr) {

		this.signupClient = signupClient;
		this.logInMgr = logInMgr;
		this.deviceInfoMgr = deviceInfoMgr;
	}

	public void signup(String username,
					   String password,
					   String passwordConfierm,
					   String email) {

		this.username = username;
		this.password = password;
		SignUpForm form = new SignUpForm(username, password, passwordConfierm, email);
		SingupError[] errs = signupClient.register(form);
		if (errs.length > 0) throw new SignupException(errs);
	}

	public boolean logIn() {
		try {
			logInMgr.loginUserPass(username, password, false);
			return true;
		} catch (LoginMgr.FailedLoginException e) {
			return false;
		}
	}

	class SignupException extends RuntimeException {

		private String message;

		public SignupException(SingupError[] errs) {
			message = parsErrs(errs);
		}

		@Override
		public String getMessage() {
			return message;
		}

		private String parsErrs(SingupError[] errors) {
			StringBuffer resultMsg = new StringBuffer();
			resultMsg.append("Errors:\n");
			Arrays.asList(errors).forEach(err ->
					resultMsg.append(err.name() + "\n"));
			return resultMsg.toString();
		}
	}
}
