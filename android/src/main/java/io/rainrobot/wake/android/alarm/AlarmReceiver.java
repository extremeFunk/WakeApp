package io.rainrobot.wake.android.alarm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Date;

import android.widget.Toast;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.util.EventUtil;
import io.rainrobot.wake.core.util.DateUtil;
import io.rainrobot.wake.util.AlarmUtil;

public class AlarmReceiver extends BroadcastReceiver {

    private AlarmEvent event;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "alarm is lunching", Toast.LENGTH_LONG);
        event = reCreateEvent(intent);
        if (AlarmUtil.isSnooze(event)) {
            showActivity(SnoozeAlarmActivity.class, context, intent);
        } else {
            showActivity(AlarmActivity.class, context, intent);
        }
    }

    private void showActivity(Class<? extends Activity> activityClass,
                              Context context,
                              Intent intent) {
        Bundle bundle = intent.getExtras();
        Intent nuIntent = new Intent(context, activityClass);
        nuIntent.putExtras(bundle);
        nuIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(nuIntent);
    }

    private AlarmEvent reCreateEvent(Intent  intent) {
        return new AndroidAlarmMgr.EventBundleConvertor()
                .toEvent(intent);
    }
}
