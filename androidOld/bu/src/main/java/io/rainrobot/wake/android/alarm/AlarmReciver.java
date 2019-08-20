package io.rainrobot.wake.android.alarm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.util.DateUtil;

public class AlarmReciver extends BroadcastReceiver {


    private AlarmEvent event;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "alarm", Toast.LENGTH_SHORT).show();
        Log.d("test log", "on recieve");
        event = reCreateEvent(intent.getExtras());
        if (isSnoozeOff() && !isShutoff()) {
            showActivity(SnoozeAlarmActivity.class, context, intent);
        } else {
            showActivity(AlarmActivity.class, context, intent);
        }
    }

    private boolean isShutoff() {
        if(event.getShutOff() == AlarmEvent.SHUTOFF_OFF) return false;
        Date nowPlusSnooze = getNowPlusSnooze();
        Log.d("reciver", nowPlusSnooze.toString());
        Date shutoffTime = getShutoffTime();
        Log.d("reciver", shutoffTime.toString());
        return shutoffTime.before(nowPlusSnooze);
    }

    private boolean isSnoozeOff() {
        return event.getSnooze() != AlarmEvent.SNOOZE_OFF;
    }

    private Date getNowPlusSnooze() {
        Date now = new Date();
        return DateUtil.plusMinuets(now, event.getSnooze());
    }

    private Date getShutoffTime() {
        Date shutOffTime = DateUtil.plusMinuets(event.getTime(), event.getShutOff());
        return DateUtil.getNextTime(shutOffTime);
    }

    private void showActivity(Class<? extends Activity> activityClass,
                              Context context,
                              Intent intent) {
        Bundle b = intent.getExtras();
        Intent nuIntent = new Intent(context, activityClass);
        nuIntent.putExtras(b);
        nuIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(nuIntent);
    }

    private AlarmEvent reCreateEvent(Bundle bundle) {
        return new AndroidAlarmMgr.EventBundleConvertor()
                .toEvent(bundle);
    }

}
