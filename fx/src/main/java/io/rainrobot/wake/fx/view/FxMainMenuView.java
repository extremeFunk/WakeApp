package io.rainrobot.wake.fx.view;

import io.rainrobot.wake.controller.MainMenuController;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.fx.view.component.FxView;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.MainMenuView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class FxMainMenuView extends FxView<MainMenuController> implements MainMenuView {
    @FXML
    Button presetsBtn;
    @FXML
    Button settingsBtn;
    @FXML
    Button deviceEventsBtn;
    @FXML
    Button logoutBtn;

    private MainMenuController ctrl;

    public FxMainMenuView(MainStageMgr loader) {
        super(loader);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLogoutCommand(ctrl.getLogoutCommand());
        setDeviceEventsCommand(ctrl.getDeviceEventsCommand());
        setSettingsCommand(ctrl.getSettingsCommand());
        setPresetsCommand(ctrl.getPresetsCommand());
    }

    @Override
    protected String getFxmlName() {
        return "main_menu";
    }

    @Override
    public void setController(MainMenuController controller) {
        this.ctrl = controller;
    }

    @Override
    public void setPresetsCommand(Command command) {
        presetsBtn.setOnAction((e) -> command.execute());
    }

    @Override
    public void setSettingsCommand(Command command) {
        settingsBtn.setOnAction((e) -> command.execute());
    }

    @Override
    public void setDeviceEventsCommand(Command command) {
        deviceEventsBtn.setOnAction((e) -> command.execute());
    }

    @Override
    public void setLogoutCommand(Command command) {
        logoutBtn.setOnAction((e) -> command.execute());
    }
}
