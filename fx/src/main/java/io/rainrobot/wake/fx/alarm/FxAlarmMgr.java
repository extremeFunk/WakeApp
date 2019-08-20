package io.rainrobot.wake.fx.alarm;

import io.rainrobot.wake.alarm.IAlarmMgr;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.util.Log;
import org.quartz.*;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class FxAlarmMgr implements IAlarmMgr {

    private static final String JOB_PREFIX = "job id ";
    private static final String GROUP = "alarm_group";
    private static final String TRIGGER_PREFIX = "trigger id ";
    private final Scheduler sched;

    public FxAlarmMgr(Scheduler sched) {
        this.sched = sched;
    }

    @Override
    public void addAlarm(AlarmEvent event, Date time) {
        String jobId = getJobId(event);
        JobDetail job = getJob(event, jobId);
        Trigger trigger = getTrigger(event, time);
        scheduleJob(job, trigger);
    }

    private void scheduleJob(JobDetail job, Trigger trigger) {
        startScheduler();
        try {
//            if (sched.checkExists(job.getKey())) {
//                sched.deleteJob(job.getKey());
//            }
            sched.scheduleJob(job, trigger);
            Log.d(this, "alarm with " + job.getKey().getName() + "was added");
        }
        catch (SchedulerException e) { Log.err(this, e);}
    }

    public Trigger getTrigger(AlarmEvent event, Date time) {
        return newTrigger()
                .withIdentity(getTriggerId(event), GROUP)
                .startAt(time)
                .build();
    }

    public JobDetail getJob(AlarmEvent event, String jobId) {
        return newJob(AlarmJob.class)
                .withIdentity(jobId, GROUP)
                .setJobData(AlarmJob.EventConverter.getMap(event))
                .build();
    }

    @Override
    public void removeAlarm(AlarmEvent event) {
        try {
            String jobId = getJobId(event);
            sched.deleteJob(new JobKey(jobId, GROUP));
            Log.d(this, "alarm with " + jobId + "was removed");
        }
        catch (SchedulerException e) { throw new RuntimeException(e); }
    }

    public void startScheduler() {
        try { if(sched.isStarted() == false) sched.start();  }
        catch (SchedulerException e) { throw new RuntimeException(e); }
    }

    private String getTriggerId(AlarmEvent event) {
        return TRIGGER_PREFIX + event.getId();
    }

    private String getJobId(AlarmEvent event) {
        return JOB_PREFIX + event.getId();
    }
}
