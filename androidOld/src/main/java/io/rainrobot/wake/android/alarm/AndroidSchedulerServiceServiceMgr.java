package io.rainrobot.wake.android.alarm;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import io.rainrobot.wake.alarm.ISchedulerServiceMgr;
import io.rainrobot.wake.core.util.Log;

public class AndroidSchedulerServiceServiceMgr implements ISchedulerServiceMgr {
    private static final int ID = 101;
    private Context context;

    public AndroidSchedulerServiceServiceMgr(Context context) {
        this.context = context;
    }

    @Override
    public void turnOn() {
        ComponentName comNm = new ComponentName(context, SchegdualerService.class);
        JobInfo info = new JobInfo.Builder(ID, comNm)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(16 * 60 * 1000)
                .build();
        try {
            JobScheduler scheduler = getJobScheduler();
            int result = scheduler.schedule(info);
            if(result == JobScheduler.RESULT_FAILURE) {
                Log.err("failed to schedule", null);
            }
        } catch (Exception e) {
            Log.err(this, e);
        }
    }

    private JobScheduler getJobScheduler() {
        return (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    @Override
    public void turnOff() {
        JobScheduler scheduler = getJobScheduler();
        scheduler.cancel(ID);
        //optional -> turn off all setLastChange alarms
    }
}
