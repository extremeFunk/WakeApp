package io.rainrobot.wake.fx.alarm;

import io.rainrobot.wake.app.EventUpdateMgr;
import io.rainrobot.wake.fx.ConfigContainer;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SchedulerServiceJob implements Job {

    private final EventUpdateMgr updater;

    public SchedulerServiceJob() {
        updater = ConfigContainer.getConfig().getEventUpdateService();
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        updater.updateEvents();
    }
}
