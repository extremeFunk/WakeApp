package io.rainrobot.wake.model;

import io.rainrobot.wake.app.IModel;
import io.rainrobot.wake.app.LoginMgr;


public class LoginModel implements IModel {

	public static final int SUCCESS = 1;
	public static final int FAIL = 0;
	private LoginMgr loginMgr;

	public LoginModel(LoginMgr loginMgr) {
		this.loginMgr = loginMgr;
	}

	public int login(String username, String password, boolean rememberMe) {
		try {
			loginMgr.loginUserPass(username, password, rememberMe);
			return SUCCESS;
		} catch (LoginMgr.FailedLoginException e) {
			return FAIL;
		}
	}

	public boolean isDeviceRegister() {
		return loginMgr.isDeviceRegister();
	}

}
