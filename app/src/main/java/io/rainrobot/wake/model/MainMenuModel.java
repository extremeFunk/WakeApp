package io.rainrobot.wake.model;

import io.rainrobot.wake.app.IModel;
import io.rainrobot.wake.app.LoginMgr;

public class MainMenuModel implements IModel {
	
	private LoginMgr loginMgr;

	public MainMenuModel(LoginMgr loginMgr) {
		this.loginMgr = loginMgr;
	}

	public void logout() {
		loginMgr.logout();
	}


}
