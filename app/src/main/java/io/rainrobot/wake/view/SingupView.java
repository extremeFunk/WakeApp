package io.rainrobot.wake.view;

import io.rainrobot.wake.app.IView;
import io.rainrobot.wake.controller.SingupController;
import io.rainrobot.wake.util.Command;


public interface SingupView extends IView<SingupController> {

	String getUsernameField();

    String getEmailField();

    String getPasswordField();

	String getPasswordConfiermField();

	void showMsg(String string);

	void setSingCommand(Command singCommand);

	void setGoBackCommand(Command goBackCommand);



}
