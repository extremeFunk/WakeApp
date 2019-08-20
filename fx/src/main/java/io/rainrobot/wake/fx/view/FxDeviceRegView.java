package io.rainrobot.wake.fx.view;

import io.rainrobot.wake.controller.DeviceRegController;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.fx.view.component.FxView;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.DeviceRegView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

public class FxDeviceRegView extends FxView<DeviceRegController> implements DeviceRegView {
    @FXML
    Button goBackBtn;
    @FXML
    TextField inputTxtField;
    @FXML
    Button registerButton;

    private DeviceRegController ctrl;

    public FxDeviceRegView(MainStageMgr loader) {
        super(loader);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        goBackBtn.setOnAction(e -> ctrl.getGoBackCommand().execute());
        setRegisterCommand(ctrl.getRegisterCommand());
    }


    @Override
    protected String getFxmlName() {
        return "device_registration";
    }

    @Override
    public void setController(DeviceRegController controller) {
        this.ctrl = controller;
    }

    @Override
    public String getInputField() {
        return inputTxtField.getText();
    }

    @Override
    public void setRegisterCommand(Command command) {
        registerButton.setOnAction((e) -> { command.execute(); });
    }
}
