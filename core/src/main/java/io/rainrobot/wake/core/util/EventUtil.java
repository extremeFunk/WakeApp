package io.rainrobot.wake.core.util;

import io.rainrobot.wake.core.AlarmEvent;

import java.util.Date;

public class EventUtil {

    private static final String OFF = "Off";

    public static String delayText(AlarmEvent event) {
        return event.getDelay() + AlarmEvent.DELAY_SUFFIX;
    }

    public static String shutoffText(AlarmEvent event) {
        if(event.getShutOff() == AlarmEvent.SHUTOFF_OFF) return OFF;
        else return event.getShutOff() + AlarmEvent.SHUTOFF_SUFFIX;
    }

    public static String snoozeText(AlarmEvent event) {
        if(event.getSnooze() == AlarmEvent.SNOOZE_OFF) return OFF;
        else return event.getSnooze() + AlarmEvent.SNOOZE_SUFFIX;
    }

    public static String volText(AlarmEvent event) {
        return AlarmEvent.VOL_PREFIX + event.getVol();
    }

    public static String soundText(AlarmEvent event) {
        return event.getSound().name();
    }

    public static String deviceText(AlarmEvent event) {
        return event.getDevice().getName();
    }

    public static boolean isSnooze(AlarmEvent event) {
        return event.getSnooze() != AlarmEvent.SNOOZE_OFF;
    }

    public static boolean isShutoff(AlarmEvent event) {
        return event.getShutOff() != AlarmEvent.SNOOZE_OFF;
    }

    public static boolean isScheduleSnooze(AlarmEvent e) {
        return getSnoozeDate(e).before(getShutoffDate(e));
    }

    public static Date getShutoffDate(AlarmEvent event) {
        return DateUtil.plusMinuets(event.getTime(), event.getShutOff());
    }

    public static Date getSnoozeDate(AlarmEvent e) {
        return DateUtil.plusMinuets(new Date(), e.getSnooze());
    }

}
