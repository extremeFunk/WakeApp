package io.rainrobot.wake.fx.view;


import io.rainrobot.wake.controller.EnterNewPasswordController;
import io.rainrobot.wake.fx.view.component.FxView;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.view.EnterNewPasswordView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FxEnterNewPassword extends FxView<EnterNewPasswordController> implements EnterNewPasswordView {

    private EnterNewPasswordController controller;

    @FXML
    TextField tokenTxtField;
    @FXML
    TextField newPasswordTxtField;
    @FXML
    TextField passwordConfirmTxtField;
    @FXML
    Button goBackButton;
    @FXML
    Button sendButton;


    public FxEnterNewPassword(MainStageMgr mainWindowMgr) {
        super(mainWindowMgr);
    }

    @Override
    protected String getFxmlName() {
        return "enter_new_password";
    }

    @Override
    public String getTokenField() {
        return tokenTxtField.getText();
    }

    @Override
    public String getNewPasswordField() {
        return newPasswordTxtField.getText();
    }

    @Override
    public String getConfirmPasswordField() {
        return passwordConfirmTxtField.getText();
    }

    @Override
    public void setController(EnterNewPasswordController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sendButton.setOnAction((e) -> controller.getSendCommand().execute());
        goBackButton.setOnAction((e) -> controller.getGoBackCommand().execute());
    }
}
