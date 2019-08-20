package io.rainrobot.wake.fx.view;

import io.rainrobot.wake.controller.SettingsController;
import io.rainrobot.wake.fx.view.component.FxView;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.SettingsView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FxSettingsView extends FxView<SettingsController>
                                implements SettingsView {

    @FXML
    Button goBackBtn;
    @FXML
    TextField inputTxtField;
    @FXML
    Button okBtn;

    private SettingsController ctrl;

    public FxSettingsView(MainStageMgr loader) {
        super(loader);
    }

    @Override
    protected String getFxmlName() {
        return "settings";
    }

    @Override
    public void setController(SettingsController controller) {
        this.ctrl = controller;
    }

    @Override
    public String getInputField() {
        return inputTxtField.getText();
    }

    @Override
    public void setRegisterCommand(Command command) {
        okBtn.setOnAction(e -> command.execute());
    }

    @Override
    public void setGoBackCommand(Command command) {
        goBackBtn.setOnAction(e -> command.execute());
    }

    @Override
    public void setDeviceName(String string) {
        inputTxtField.setText(string);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ctrl.injectDataToView();
        setGoBackCommand(ctrl.getGoBackCommand());
        setRegisterCommand(ctrl.getDeviceNameCommand());
    }
}
