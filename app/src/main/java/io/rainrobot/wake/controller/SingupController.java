package io.rainrobot.wake.controller;

import io.rainrobot.wake.app.Controller;
import io.rainrobot.wake.app.ControllerMgr;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.SingupView;
import io.rainrobot.wake.model.SighupModel;


public class SingupController extends Controller<SingupView, SighupModel> {


	public SingupController(SingupView view,
							SighupModel model,
							ControllerMgr controllerMgr,
							ASyncProvider aSyncProvider) {

		super(view, model, controllerMgr, aSyncProvider);
	}


	public Command getGoBackCommand() {
		return () -> controllerMgr.showLogin();
	}

	public Command getAlradyRegisterdCommand() {
		return controllerMgr::showLogin;
	}

	public Command getSingCommand() {
		return (() -> {
			asyncCall(
					(() -> {
							model.signup(
								view.getUsernameField(),
								view.getPasswordField(),
								view.getPasswordConfiermField(),
								view.getEmailField());
						}),
					(() -> {
						asyncCall(
								() -> {
									return model.logIn();
								},
								((flag)-> acceptLoginResult(flag)));
						}));
		});
	}

	private void acceptLoginResult(boolean flag) {
		if(flag) { controllerMgr.showDeviceRegister(); }
		else { view.showMsg("Failed to login"); }

	}
}


