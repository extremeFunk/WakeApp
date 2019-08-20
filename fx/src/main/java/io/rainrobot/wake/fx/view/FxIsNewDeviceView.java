package io.rainrobot.wake.fx.view;

import io.rainrobot.wake.controller.IsNewDeviceController;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.fx.view.component.FxView;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.IsNewDeviceView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class FxIsNewDeviceView extends FxView<IsNewDeviceController> implements IsNewDeviceView {
    @FXML
    Button yesButton;
    @FXML
    Button noButton;
    private IsNewDeviceController ctrl;

    public FxIsNewDeviceView(MainStageMgr loader) {
        super(loader);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDeviceExistCommand(ctrl.getDeviceExistCommand());
        setDeviceNotExistCommand(ctrl.getDeviceNotExistCommand());
    }


    @Override
    protected String getFxmlName() {
        return "is_new_device";
    }

    @Override
    public void setController(IsNewDeviceController controller) {
        this.ctrl = controller;
    }

    @Override
    public void setDeviceNotExistCommand(Command command) {
        yesButton.setOnAction((e) -> command.execute());
    }

    @Override
    public void setDeviceExistCommand(Command command) {
        noButton.setOnAction((e) -> command.execute());
    }
}
