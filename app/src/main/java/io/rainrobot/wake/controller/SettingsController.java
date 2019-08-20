package io.rainrobot.wake.controller;


import io.rainrobot.wake.app.Controller;
import io.rainrobot.wake.app.ControllerMgr;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.model.SettingsModel;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.SettingsView;


public class SettingsController extends Controller<SettingsView, SettingsModel> {

	public SettingsController(SettingsView view,
							  SettingsModel model,
							  ControllerMgr controllerMgr,
							  ASyncProvider aSyncProvider) {

		super(view, model, controllerMgr, aSyncProvider);
	}

	public Command getGoBackCommand() {
		return controllerMgr::showMainMenu;
	}


	public void injectDataToView() {
		asyncCall(model::getDeviceName,
				(name) -> view.setDeviceName(name));
	}


	public Command getDeviceNameCommand() {
		return (() -> {
			String input = view.getInputField();
			if(input == "") view.showMsg("Device name cannot be empty");
			asyncCall((() -> {
						model.registerDevice(input);
					}),
							controllerMgr::showMainMenu);
		});
	}
}


