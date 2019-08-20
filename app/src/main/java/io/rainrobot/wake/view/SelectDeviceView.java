package io.rainrobot.wake.view;

import java.util.List;

import io.rainrobot.wake.app.IView;
import io.rainrobot.wake.controller.SelectDeviceController;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.util.Command;

public interface SelectDeviceView extends IView<SelectDeviceController> {
    void addDev(List<Device> dev);
}
