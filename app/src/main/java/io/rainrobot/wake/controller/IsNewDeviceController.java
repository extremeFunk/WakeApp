package io.rainrobot.wake.controller;

import io.rainrobot.wake.app.Controller;
import io.rainrobot.wake.app.ControllerMgr;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.IsNewDeviceView;
import io.rainrobot.wake.model.IsNewDeviceModel;

public class IsNewDeviceController extends Controller<IsNewDeviceView, IsNewDeviceModel> {


	public IsNewDeviceController(IsNewDeviceView view,
								 IsNewDeviceModel model,
								 ControllerMgr controllerMgr,
								 ASyncProvider aSyncProvider) {

		super(view, model, controllerMgr, aSyncProvider);
	}

	public Command getDeviceNotExistCommand() {
		return controllerMgr::showDeviceRegister;
	}

	public Command getDeviceExistCommand() {
		return controllerMgr::showSelectDevice;
	}
}
