package io.rainrobot.wake.controller;

import io.rainrobot.wake.app.AppLogger;
import io.rainrobot.wake.app.Controller;
import io.rainrobot.wake.app.ControllerMgr;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.MainMenuView;
import io.rainrobot.wake.model.MainMenuModel;

public class MainMenuController extends Controller<MainMenuView, MainMenuModel> {

	private final AppLogger logger;

	public MainMenuController(MainMenuView view,
							  MainMenuModel model,
							  ControllerMgr controllerMgr,
							  ASyncProvider aSyncProvider, AppLogger logger) {

		super(view, model, controllerMgr, aSyncProvider);
		this.logger = logger;
	}

	public Command getPresetsCommand() {
		return controllerMgr::showPresets;
	}
	public Command getSettingsCommand() {
		return controllerMgr::showSettings;
	}
	public Command getDeviceEventsCommand() {
		return controllerMgr::showDeviceEvents;
	}
	public Command getLogoutCommand() {
		return () -> asyncCall(logger::logout,
								controllerMgr::showLogin);
	}

}
