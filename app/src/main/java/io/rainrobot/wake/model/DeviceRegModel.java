package io.rainrobot.wake.model;

import io.rainrobot.wake.app.DeviceInfoMgr;
import io.rainrobot.wake.app.IModel;
import io.rainrobot.wake.client.AccountClient;
import io.rainrobot.wake.client.DeviceClient;
import io.rainrobot.wake.core.Device;

public class DeviceRegModel implements IModel {

    private DeviceClient deviceClient;
    private AccountClient accountClient;
    private DeviceInfoMgr deviceInfoMgr;

    public DeviceRegModel(DeviceClient client,
                          AccountClient accountClient,
                          DeviceInfoMgr deviceInfoMgr) {
        this.deviceClient = client;
        this.accountClient = accountClient;
        this.deviceInfoMgr = deviceInfoMgr;
    }

    public void registerDevice(String deviceName) {
        checkIfNameTaken(deviceName);
        Device device = deviceClient.createDevice();
        device.setName(deviceName);
        deviceClient.updateDevice(device);
        deviceInfoMgr.setDeviceName(deviceName);
    }

    private void checkIfNameTaken(String name) {
        for(Device dev : deviceClient.getAllDevices()) {
            if(dev.getName() == name) {
                throw new DeviceNameTakenException();
            }
        }
    }
}
