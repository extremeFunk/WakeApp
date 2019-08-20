package io.rainrobot.wake.view;

import io.rainrobot.wake.app.IView;
import io.rainrobot.wake.controller.MainMenuController;
import io.rainrobot.wake.util.Command;

public interface MainMenuView extends IView<MainMenuController> {

	void setPresetsCommand(Command command);
	
	void setSettingsCommand(Command command);
	
	void setDeviceEventsCommand(Command command);
	
	void setLogoutCommand(Command command);
}
