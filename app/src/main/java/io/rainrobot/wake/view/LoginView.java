package io.rainrobot.wake.view;

import io.rainrobot.wake.app.IView;
import io.rainrobot.wake.controller.LoginController;
import io.rainrobot.wake.util.Command;

public interface LoginView extends IView<LoginController> {

	void setLoginCommand(Command command);

	void setSingupCommand(Command command);

	void setForgotPasswordCommand(Command command);

	String getUsernameField();

	String getPasswordField();

	Boolean isRememberMe();

}
