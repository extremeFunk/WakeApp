package io.rainrobot.wake.alarm;

import java.util.Date;

import io.rainrobot.wake.core.AlarmEvent;

public interface IAlarmMgr {

    void addAlarm(AlarmEvent e, Date time);

    void removeAlarm(AlarmEvent e);
}
