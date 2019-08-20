package io.rainrobot.wake.controller;

import io.rainrobot.wake.app.AppLogger;
import io.rainrobot.wake.app.Controller;
import io.rainrobot.wake.app.ControllerMgr;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.model.DeviceRegModel;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.DeviceRegView;

public class DeviceRegController extends Controller<DeviceRegView, DeviceRegModel> {

    private AppLogger logger;

    public DeviceRegController(DeviceRegView view,
                               DeviceRegModel model,
                               ControllerMgr controllerMgr,
                               ASyncProvider aSyncProvider,
                               AppLogger logger) {

        super(view, model, controllerMgr, aSyncProvider);
        this.logger = logger;
    }

    public Command getGoBackCommand() {
        return (() -> { controllerMgr.showIsNewDevice(); });
    }

    public Command getRegisterCommand() {
        return (() -> {
            String deviceName = view.getInputField();
            if(deviceName == "") view.showMsg("Pleas enter a name");
            asyncCall(() -> model.registerDevice(deviceName),
                    () -> logger.login());
        });
    }

}