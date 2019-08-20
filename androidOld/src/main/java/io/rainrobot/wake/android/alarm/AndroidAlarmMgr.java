package io.rainrobot.wake.android.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Date;

import android.widget.Toast;
import io.rainrobot.wake.alarm.IAlarmMgr;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.core.Sound;
import io.rainrobot.wake.core.util.DateUtil;
import io.rainrobot.wake.core.util.Log;

public class AndroidAlarmMgr implements IAlarmMgr {

    private Context context;
    public static final String BUNDLE = "bundle";

    public AndroidAlarmMgr(Context context) {
        this.context = context;
    }

    @Override
    public void removeAlarm(AlarmEvent e) {
        AlarmManager mgr = getAlarmManager();
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pend = PendingIntent.getBroadcast(context, e.getId(), intent, 0);
        mgr.cancel(pend);
    }
    @Override
    public void addAlarm(AlarmEvent e, Date time) {
        Log.d(this, "adding event " + e.toString());
        AlarmManager mgr = getAlarmManager();
        PendingIntent pend = getPendingIntent(e);
        mgr.setExact(AlarmManager.RTC_WAKEUP, time.getTime(), pend);
        Log.d(this, "event was added with id: " + e.getId());
    }


    private PendingIntent getPendingIntent(AlarmEvent e) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        new EventBundleConvertor().addEventToInten(e, intent);
        return PendingIntent.getBroadcast(context,
                e.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
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


        public void addEventToInten(AlarmEvent e, Intent intent) {
            Bundle bundle = new Bundle();

            bundle.putInt(ID, e.getId());
            bundle.putInt(SNOOZE, e.getSnooze());
            bundle.putInt(SHUTOFF, e.getShutOff());
            bundle.putInt(VOL, e.getVol());
            bundle.putString(SOUND, e.getSound().name());
            bundle.putLong(TIME, e.getTime().getTime());

            intent.putExtra(BUNDLE, bundle);
//            intent.putExtra(ID, e.getId());
//            intent.putExtra(SNOOZE, e.getSnooze());
//            intent.putExtra(SHUTOFF, e.getShutOff());
//            intent.putExtra(VOL, e.getVol());
//            intent.putExtra(SOUND, e.getSound().name());
//            intent.putExtra(TIME, e.getTime().getTime());
        }

        public AlarmEvent toEvent(Intent intent) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            int id = bundle.getInt(ID);
            if (id == -1) throw new RuntimeException("no extras");
            int snooze = bundle.getInt(SNOOZE, -1);
            int shutoff = bundle.getInt(SHUTOFF, -1);
            int vol = bundle.getInt(VOL, 100);
            String soundNm = bundle.getString(SOUND);
            Sound sound = Sound.valueOf(soundNm);
            long timeInMili = bundle.getLong(TIME, 0);
//            int id = intent.getIntExtra(ID, -1);
//            if (id == -1) throw new RuntimeException("no extras");
//            int snooze = intent.getIntExtra(SNOOZE, -1);
//            int shutoff = intent.getIntExtra(SHUTOFF, -1);
//            int vol = intent.getIntExtra(VOL, 100);
//            String soundNm = intent.getStringExtra(SOUND);
//            Sound sound = Sound.valueOf(soundNm);
//            long timeInMili = intent.getLongExtra(TIME, 0);
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




