package io.rainrobot.wake.fx.alarm;

import io.rainrobot.wake.alarm.ISchedulerServiceMgr;
import io.rainrobot.wake.core.util.Log;
import org.quartz.*;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class FxSchedulerServiceMgr implements ISchedulerServiceMgr {

    private static final String GROUP = "scheduler_service_group";
    private static final String TRIGGER_ID = "scheduler_service_trigger_id";
    private static final String JOB_ID = "scheduler_service_job_id";
    private static final int REPEAT_INTERVAL = 1;
    private final Scheduler sched;

    public FxSchedulerServiceMgr(Scheduler sched) {
        this.sched = sched;
    }

    @Override
    public void turnOn() {
        if(!isSchedStarted()) { startSched(); }
        try {
            sched.scheduleJob(getJob(), getTrigger());
            Log.d(this, "Scheduler service job was scheduled");
        }
        catch (SchedulerException e) { throw new RuntimeException(e);}
    }

    @Override
    public void turnOff() {
        try {
            sched.deleteJob(new JobKey(JOB_ID, GROUP));
            Log.d(this, "Scheduler service job was unscheduled");
        }
        catch (SchedulerException e) { throw new RuntimeException(e); }
    }

    public Trigger getTrigger() {
        return newTrigger()
                .withIdentity(TRIGGER_ID, GROUP)
                .startNow()
                .withSchedule(SimpleScheduleBuilder
                                .simpleSchedule()
                                .repeatForever()
                                .withIntervalInMinutes(REPEAT_INTERVAL))
                .build();
    }

    public JobDetail getJob() {
        return newJob(SchedulerServiceJob.class)
                .withIdentity(JOB_ID, GROUP)
                .build();
    }

    private void startSched() {
        try { sched.start(); }
        catch (SchedulerException e) { throw new RuntimeException(e); }
    }

    private boolean isSchedStarted() {
        try { return sched.isStarted(); }
        catch (SchedulerException e) { throw new RuntimeException(e); }
    }
}
