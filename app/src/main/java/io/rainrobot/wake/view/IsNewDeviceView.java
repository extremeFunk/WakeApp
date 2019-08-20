package io.rainrobot.wake.view;

import io.rainrobot.wake.app.IView;
import io.rainrobot.wake.controller.IsNewDeviceController;
import io.rainrobot.wake.util.Command;

public interface IsNewDeviceView extends IView<IsNewDeviceController> {

    void setDeviceNotExistCommand(Command command);

    void setDeviceExistCommand(Command command);

    void show();

}
