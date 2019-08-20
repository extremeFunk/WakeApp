package io.rainrobot.wake.android.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;

import io.rainrobot.wake.alarm.IAlarmMgr;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.core.Sound;
import io.rainrobot.wake.util.DateUtil;

public class AndroidAlarmMgr implements IAlarmMgr {

    private Context context;
    public static final String BUNDLE = "bundle";

    public AndroidAlarmMgr(Context context) {
        this.context = context;
    }

    @Override
    public void removeAlarm(AlarmEvent e) {
        AlarmManager mgr = getAlarmManager();
        Intent intent = new Intent(context, AlarmReciver.class);
        PendingIntent pend = PendingIntent.getBroadcast(context, e.getId(), intent, 0);
        mgr.cancel(pend);
    }
    @Override
    public void addAlarm(AlarmEvent e, Date time) {
        AlarmManager mgr = getAlarmManager();
        PendingIntent pend = getPendingIntent(e);
        mgr.setExact(AlarmManager.RTC_WAKEUP, procTime(time), pend);
    }

    private long procTime(Date time) {
        time = DateUtil.getNextTime(time);
        Log.d("test log", "time is " + time);
        return time.getTime();
    }

    private PendingIntent getPendingIntent(AlarmEvent e) {
        Intent intent = new Intent(context, AlarmReciver.class);
        new EventBundleConvertor().addToIntent(e, intent);
        return PendingIntent.getBroadcast(context, e.getId(), intent, 0);
    }

    private AlarmManager getAlarmManager() {
        return (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    }

    public static class EventBundleConvertor {

        public static final String SNOOZE = "snooze";
        public static final String SHUTOFF = "shutoff";
        public static final String VOL = "vol";
        public static final String SOUND = "sound";
        public static final String TIME = "time";
        public static final String ID = "id";


        public void addToIntent(AlarmEvent e, Intent intent) {
            intent.putExtra(ID, e.getId());
            intent.putExtra(SNOOZE, e.getSnooze());
            intent.putExtra(SHUTOFF, e.getShutOff());
            intent.putExtra(VOL, e.getVol());
            intent.putExtra(SOUND, e.getSound().name());
            intent.putExtra(TIME, e.getTime().getTime());
        }

        public AlarmEvent toEvent(Bundle bundle) {
            int id = bundle.getInt(ID, -1);
            int snooze = bundle.getInt(SNOOZE, -1);
            int shutoff = bundle.getInt(SHUTOFF, -1);
            int vol = bundle.getInt(VOL, 100);
            String soundNm = bundle.getString(SOUND);
            Sound sound = Sound.valueOf(soundNm);
            long timeInMili = bundle.getLong(TIME, 0);
            Preset preset = new Preset();
            preset.setTime(DateUtil.fromMili(timeInMili));
            return new AlarmEvent.Builder()
                    .setId(id)
                    .setSnooze(snooze)
                    .setShutOff(shutoff)
                    .setVol(vol)
                    .setSound(sound)
                    .setPreset(preset)
                    .build();
        }
    }
}




