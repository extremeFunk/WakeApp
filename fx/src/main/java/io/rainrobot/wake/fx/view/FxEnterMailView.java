package io.rainrobot.wake.fx.view;

import io.rainrobot.wake.controller.EnterEmailController;
import io.rainrobot.wake.fx.view.component.FxView;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.view.EnterEmailView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FxEnterMailView extends FxView<EnterEmailController> implements EnterEmailView {

    private EnterEmailController controller;

    @FXML
    TextField emailTxtField;
    @FXML
    Button sendButton;
    @FXML
    Button goBackButton;

    public FxEnterMailView(MainStageMgr mainWindowMgr) {
        super(mainWindowMgr);
    }

    @Override
    protected String getFxmlName() {
        return "enter_email";
    }

    @Override
    public void setController(EnterEmailController controller) {
        this.controller = controller;
    }

    @Override
    public String getEmailField() {
        return emailTxtField.getText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sendButton.setOnAction((e) -> controller.getSendCommand().execute());
        goBackButton.setOnAction((e) -> controller.getGoBackCommand().execute());
    }
}
