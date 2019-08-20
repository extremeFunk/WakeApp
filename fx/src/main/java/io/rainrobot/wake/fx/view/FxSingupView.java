package io.rainrobot.wake.fx.view;

import io.rainrobot.wake.controller.SingupController;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.fx.view.component.FxView;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.SingupView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FxSingupView extends FxView<SingupController> implements SingupView {

    @FXML
    TextField usernameTxtField;
    @FXML
    TextField emailTxtField;
    @FXML
    TextField passwordTxtField;
    @FXML
    TextField passwordConfirmTxtField;
    @FXML
    Button goBackButton;
    @FXML
    Button singupButton;

    private SingupController ctrl;

    public FxSingupView(MainStageMgr loader) {
        super(loader);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setGoBackCommand(ctrl.getGoBackCommand());
        setSingCommand(ctrl.getSingCommand());
    }


    @Override
    protected String getFxmlName() {
        return "singup";
    }

    @Override
    public String getUsernameField() {
        return usernameTxtField.getText();
    }

    @Override
    public String getEmailField() { return emailTxtField.getText(); }

    @Override
    public String getPasswordField() {
        return passwordTxtField.getText();
    }

    @Override
    public String getPasswordConfiermField() {
        return passwordConfirmTxtField.getText();
    }

    @Override
    public void setSingCommand(Command command) {
        singupButton.setOnAction((e) -> command.execute());
    }

    @Override
    public void setGoBackCommand(Command command) {
        goBackButton.setOnAction((e) -> command.execute());
    }

    @Override
    public void setController(SingupController singupController) {
        this.ctrl = singupController;
    }
}
