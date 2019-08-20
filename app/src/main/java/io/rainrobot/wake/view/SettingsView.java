package io.rainrobot.wake.view;

import io.rainrobot.wake.app.IView;
import io.rainrobot.wake.controller.SettingsController;
import io.rainrobot.wake.util.Command;

public interface SettingsView extends IView<SettingsController> {


	String getInputField();
	
	void showMsg(String string);

	void setRegisterCommand(Command command);

	void setGoBackCommand(Command command);

	void setDeviceName(String string);



}
