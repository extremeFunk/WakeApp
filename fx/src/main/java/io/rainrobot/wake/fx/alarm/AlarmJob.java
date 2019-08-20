package io.rainrobot.wake.fx.alarm;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.core.Sound;
import io.rainrobot.wake.core.util.DateUtil;
import io.rainrobot.wake.fx.ConfigContainer;
import io.rainrobot.wake.fx.alarm.mvc.AlarmControllerMgr;
import io.rainrobot.wake.util.AlarmUtil;
import javafx.application.Platform;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class AlarmJob implements Job {
    AlarmControllerMgr ctrlMgr;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Platform.runLater(() -> {
            ctrlMgr = ConfigContainer.getConfig().getAlarmControllerMgr();

            AlarmEvent event
                    = EventConverter.getEvent(context.getJobDetail().getJobDataMap());

            if(AlarmUtil.isSnooze(event)) { ctrlMgr.showWithSnooze(event); }
            else { ctrlMgr.showNoSnooze(event); }
        });
    }

    public static class EventConverter {
        private static final String ID = "id";
        private static final String DELAY = "delay";
        private static final String SHUTOFF = "shutoff";
        private static final String SNOOZE = "snooze";
        private static final String VOL = "vol";
        private static final String SOUND = "sound";
        private static final String DATE = "date";

        public static JobDataMap getMap(AlarmEvent event) {
            JobDataMap map = new JobDataMap();

            map.put(ID, event.getId());
            map.put(DELAY, event.getDelay());
            map.put(SHUTOFF, event.getShutOff());
            map.put(SNOOZE, event.getSnooze());
            map.put(VOL, event.getVol());
            map.put(SOUND, event.getSound().name());
            map.put(DATE, event.getPreset().getTime().getTime());

            return map;
        }

        public static AlarmEvent getEvent(JobDataMap map) {
            return new AlarmEvent.Builder()
                    .setId(map.getInt(ID))
                    .setDelay(map.getInt(DELAY))
                    .setPreset(getPreset(map))
                    .setShutOff(map.getInt(SHUTOFF))
                    .setSnooze(map.getInt(SNOOZE))
                    .setSound(getSound(map))
                    .setVol(map.getInt(VOL))
                    .setDevice(null)
                    .build();
        }

        private static Sound getSound(JobDataMap map) {
            return Sound.valueOf(map.getString(SOUND));
        }

        private static Preset getPreset(JobDataMap map) {
            Preset preset = new Preset();
            preset.setTime(DateUtil.fromMili(map.getLong(DATE)));
            return preset;
        }
    }
}
