package io.rainrobot.wake.fx.alarm.mvc;

import io.rainrobot.wake.core.AlarmEvent;

public class AlarmControllerMgr {
    private final SnoozeController snoozeCtrl;
    private final NoSnoozeController noSnoozeCtrl;

    public AlarmControllerMgr(SnoozeController snoozeCtrl, NoSnoozeController noSnoozeCtrl) {
        this.snoozeCtrl = snoozeCtrl;
        this.noSnoozeCtrl = noSnoozeCtrl;
    }

    public void showNoSnooze(AlarmEvent event) {
        noSnoozeCtrl.show(event);
    }

    public void showWithSnooze(AlarmEvent event) {
        snoozeCtrl.show(event);
    }

}
