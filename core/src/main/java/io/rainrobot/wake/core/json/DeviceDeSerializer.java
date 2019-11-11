package io.rainrobot.wake.core.json;

import io.rainrobot.wake.core.Device;

public class DeviceDeSerializer extends IdabelDeSerializer<Device>{
    @Override
    protected Device getInstance() {
        return new Device();
    }
}
