package io.rainrobot.wake.fx.alarm.mvc;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.fx.alarm.mvc.view.SnoozeView;

public class SnoozeController  {
    private final SnoozeModel model;
    private final SnoozeView view;

    public SnoozeController(SnoozeView view, SnoozeModel model) {
        this.view = view;
        view.setCtrl(this);
        this.model = model;
    }
    
    public Runnable getSnoozeCmd() {
        return () -> {
            model.scheduleNextSnooze();
            view.close();
        };
    }

    public void show(AlarmEvent event) {
        model.setSnooze(event);
        view.show(event);
    }

    public Runnable getTurnoffCmd() {
        return () -> {
            view.close();
        };
    }
}
