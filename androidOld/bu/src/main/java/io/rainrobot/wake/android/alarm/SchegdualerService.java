package io.rainrobot.wake.android.alarm;

import android.app.job.JobParameters;
import android.app.job.JobService;

import java.util.function.Consumer;

import io.rainrobot.wake.android.configuration.AndroidAppConfiguration;
import io.rainrobot.wake.android.configuration.WakeApplication;
import io.rainrobot.wake.app.EventUpdateMgr;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.core.util.Log;

public class SchegdualerService extends JobService {

    private ASyncProvider aSync;
    private EventUpdateMgr eventUpdateService;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidAppConfiguration config = new AndroidAppConfiguration(getWakeApplication());
        Log.d(this, "onCreate");
        aSync = config.getAsyncProvider();
        eventUpdateService = config.getEventUpdateService();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(this, "onJobStarted");
        doInBackground(params);
        return true;
    }

    private void doInBackground(JobParameters params) {
        aSync.asyncCall(
                () -> {
                    Log.d(this, "aSynd do first");
                    eventUpdateService.updateEvents();
                },
                    new ExceptionHandler(),
                () -> {
                    Log.d(this, "aSynd doLast");
                    jobFinished(params, true);
                });
    }

    private WakeApplication getWakeApplication() {
        return (WakeApplication) getApplication();
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(this, "onStopJob");
        aSync.cancel();
        //return = reschedule
        return true;
    }

}

class ExceptionHandler implements Consumer<Exception> {

    @Override
    public void accept(Exception e) {
        Log.err("JobService", e);
    }
}