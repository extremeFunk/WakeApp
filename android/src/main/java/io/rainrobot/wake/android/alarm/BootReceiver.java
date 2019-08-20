package io.rainrobot.wake.android.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        new AndroidSchedulerServiceServiceMgr(context).turnOn();
    }
}
