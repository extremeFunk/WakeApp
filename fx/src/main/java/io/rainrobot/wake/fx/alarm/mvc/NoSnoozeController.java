package io.rainrobot.wake.fx.alarm.mvc;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.fx.alarm.mvc.view.NoSnoozeView;

public class NoSnoozeController {

    private final NoSnoozeView view;

    public NoSnoozeController(NoSnoozeView view) {
        this.view = view;
    }

    public Runnable getTurnoffCmd() {
        return () -> { view.close(); };
    }

    public void show(AlarmEvent event) {
        view.show(event);
    }
}
