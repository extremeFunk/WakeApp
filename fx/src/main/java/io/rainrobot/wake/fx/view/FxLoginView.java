package io.rainrobot.wake.fx.view;

import io.rainrobot.wake.controller.LoginController;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.fx.view.component.FxView;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.LoginView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FxLoginView extends FxView<LoginController> implements LoginView {

	private LoginController controller;

	public FxLoginView(MainStageMgr loader) {
		super(loader);
	}

	@FXML
	Button loginBtn;
	@FXML
	Button singupBtn;
	@FXML
	Button forgotPasswordBtn;
	@FXML
	private TextField userNameTF;
	@FXML
	private PasswordField passwordTF;
	@FXML
	private CheckBox isRMCB;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setLoginCommand(controller.getLoginCommand());
		setSingupCommand(controller.getSingupCommand());
		setForgotPasswordCommand(controller.getForgotPasswordCommand());
	}

	@Override
	protected String getFxmlName() {return "login"; }

	@Override
	public void setController(LoginController controller) {
		this.controller = controller;
	}

	@Override
	public void setLoginCommand(Command command) {
		loginBtn.setOnAction((event) -> command.execute());
	}

	@Override
	public void setSingupCommand(Command command) {
		singupBtn.setOnAction((event) -> command.execute());
	}

	@Override
	public void setForgotPasswordCommand(Command forgotPasswordCommand) {
		forgotPasswordBtn.setOnAction((e) -> forgotPasswordCommand.execute());
	}

	@Override
	public String getUsernameField() {
		return userNameTF.getText();
	}

	@Override
	public String getPasswordField() {
		return passwordTF.getText();
	}

	@Override
	public Boolean isRememberMe() {
		return isRMCB.isSelected();
	}

}
