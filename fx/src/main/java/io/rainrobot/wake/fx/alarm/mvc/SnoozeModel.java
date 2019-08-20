package io.rainrobot.wake.fx.alarm.mvc;

import io.rainrobot.wake.alarm.IAlarmMgr;
import io.rainrobot.wake.app.IModel;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.util.EventUtil;

public class SnoozeModel implements IModel {

    private final IAlarmMgr alarmMgr;
    private AlarmEvent event;

    public SnoozeModel(IAlarmMgr alarmMgr) {
        this.alarmMgr = alarmMgr;
    }

    public void scheduleNextSnooze() {
        alarmMgr.addAlarm(event, EventUtil.getSnoozeDate(event));
    }

    public void setSnooze(AlarmEvent event) {
        this.event = event;
    }

    public boolean isSnooze() {
        return EventUtil.isScheduleSnooze(event);
    }
}
