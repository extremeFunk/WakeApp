package io.rainrobot.wake.view;

import io.rainrobot.wake.app.IView;
import io.rainrobot.wake.controller.DeviceRegController;
import io.rainrobot.wake.util.Command;

public interface DeviceRegView extends IView<DeviceRegController> {

	String getInputField();

	void setRegisterCommand(Command command);

}
