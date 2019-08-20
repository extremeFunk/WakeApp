package io.rainrobot.wake.model;

import io.rainrobot.wake.app.DeviceInfoMgr;
import io.rainrobot.wake.app.IModel;
import io.rainrobot.wake.core.AlarmEvent;


public class DeviceEventsModel implements IModel {

    private DeviceInfoMgr devInfo;

    public DeviceEventsModel(DeviceInfoMgr devInfo) { this.devInfo = devInfo; }

    public AlarmEvent[] getFromDb() {
        return devInfo.getEvents();
    }
}
