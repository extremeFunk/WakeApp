package io.rainrobot.wake.controller;

import io.rainrobot.wake.app.Controller;
import io.rainrobot.wake.app.ControllerMgr;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.DeviceEventsView;
import io.rainrobot.wake.model.DeviceEventsModel;

public class DeviceEventsController extends Controller<DeviceEventsView, DeviceEventsModel> {

	public DeviceEventsController(DeviceEventsView view,
									   DeviceEventsModel model,
									   ControllerMgr controllerMgr, ASyncProvider aSyncProvider) {
		super(view, model, controllerMgr, aSyncProvider);
	}

	public Command getGoBackCmd() {
		return controllerMgr::showMainMenu;
	}


	public void injectDataToView() {
		asyncCall(() -> model.getFromDb(),
				(data) -> view.setEvents(data));
	}
}
