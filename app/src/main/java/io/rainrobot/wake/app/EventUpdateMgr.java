package io.rainrobot.wake.app;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.rainrobot.wake.alarm.AlarmUpdater;
import io.rainrobot.wake.client.AccountClient;
import io.rainrobot.wake.client.DeviceClient;
import io.rainrobot.wake.client.PresetClient;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.core.util.Log;

public class EventUpdateMgr {

    private AccountClient accountClient;
    private DeviceClient deviceClient;
    private PresetClient presetClient;
    private LoginMgr loginMgr;
    private AlarmUpdater alarmUpdater;
    private DeviceInfoMgr deviceInfoMgr;

    private EventUpdateMgr(Builder b) {
        this.deviceClient = b.deviceClient;
        this.accountClient = b.accountClient;
        this.presetClient = b.presetClient;
        this.loginMgr = b.loginMgr;
        this.alarmUpdater = b.alarmUpdater;
        this.deviceInfoMgr = b.deviceInfoMgr;
    }

    public void updateEvents() {
        if(!isUserLogged()) {
            Log.d(this, "No Valid Token, Can't update events");
            clearEvents();
            return;
        }
        else {
            Date lastChange = accountClient.getLastChange();
            if (isUpdateNeeded(lastChange)) {
                alarmUpdater.update(getEventsFromServer());
                deviceInfoMgr.setLastEventsChange(lastChange);
                Log.d(this, "Events were updated");
            }
            else { Log.d(this, "No changes were made"); }
        }

    }

    public void clearEvents() {
        alarmUpdater.clear();
    }

    private boolean isUserLogged() {
        return loginMgr.isTokenValid();
    }

    private boolean isUpdateNeeded(Date lastChange) {
        return deviceInfoMgr.getEventLastChange() != lastChange;
    }

    private String getDeviceName() {
        return deviceInfoMgr.getDeviceName();
    }

    private AlarmEvent[] getEventsFromServer() {
        Map<Integer, Preset> presetMap = new HashMap<>();
        AlarmEvent[] events = deviceClient.getEventsByName(getDeviceName());
        for (AlarmEvent e : events) {
            CheckIfExistAndLoadPreset(e.getPreset().getId(), presetMap);
            e.setPreset(presetMap.get(e.getPreset().getId()));
        }
        return events;
    }

    private void CheckIfExistAndLoadPreset(Integer presetId, Map<Integer, Preset> presetMap) {
        if(!presetMap.keySet().contains(presetId)) {
            Preset p = presetClient.getById(presetId);
            if(p == null) throw new NullPointerException("client did not retrieved preset");
            presetMap.put(presetId, p);
        }
    }

    public static class Builder {
        private AccountClient accountClient;
        private DeviceClient deviceClient;
        private PresetClient presetClient;
        private LoginMgr loginMgr;
        private AlarmUpdater alarmUpdater;
        private DeviceInfoMgr deviceInfoMgr;

        public EventUpdateMgr build() {
            return new EventUpdateMgr(this);
        }

        public Builder setAccountClient(AccountClient accountClient) {
            this.accountClient = accountClient;
            return this;
        }

        public Builder setDeviceClient(DeviceClient deviceClient) {
            this.deviceClient = deviceClient;
            return this;
        }

        public Builder setPresetClient(PresetClient presetClient) {
            this.presetClient = presetClient;
            return this;
        }

        public Builder setLoginMgr(LoginMgr loginMgr) {
            this.loginMgr = loginMgr;
            return this;
        }

        public Builder setAlarmUpdater(AlarmUpdater alarmUpdater) {
            this.alarmUpdater = alarmUpdater;
            return this;
        }

        public Builder setDeviceInfoMgr(DeviceInfoMgr deviceInfoMgr) {
            this.deviceInfoMgr = deviceInfoMgr;
            return this;
        }
    }
}
