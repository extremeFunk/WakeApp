package io.rainrobot.wake.controller;

import java.util.function.Consumer;

import io.rainrobot.wake.app.AppLogger;
import io.rainrobot.wake.app.Controller;
import io.rainrobot.wake.app.ControllerMgr;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.SelectDeviceView;
import io.rainrobot.wake.model.SelectDeviceModel;

public class SelectDeviceController extends Controller<SelectDeviceView, SelectDeviceModel> {


	private final AppLogger logger;

	public SelectDeviceController(SelectDeviceView view,
								  SelectDeviceModel model,
								  ControllerMgr controllerMgr,
								  ASyncProvider aSyncProvider,
								  AppLogger logger) {

		super(view, model, controllerMgr, aSyncProvider);
		this.logger = logger;
	}

	public Consumer<Device> getSelectCommand() {
			return ((device) -> {
				model.setThisDevice(device);
				asyncCall(logger::login);
		});
	}

	public Command getGoBackCommand() {
		return controllerMgr::showIsNewDevice;
	}

	public void addDevices() {
		asyncCall((
				() -> model.getDevices()),
				((list) ->  {
					if(list.isEmpty()) {
						controllerMgr.showIsNewDevice();
					} else { view.addDev(list); }
				}));
	}


}
