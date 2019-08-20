package io.rainrobot.wake.util;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.util.DateUtil;
import io.rainrobot.wake.core.util.EventUtil;

import java.util.Date;

public class AlarmUtil {

    public static boolean isShutoffCancelSnooze(AlarmEvent event) {
        if(!EventUtil.isShutoff(event)) return false;
        Date nowPlusSnooze = getNowPlusSnooze(event);
        Date shutoffTime = getShutoffTime(event);
        return shutoffTime.before(nowPlusSnooze);
    }

    public static Date getNowPlusSnooze(AlarmEvent event) {
        Date now = new Date();
        return DateUtil.plusMinuets(now, event.getSnooze());
    }

    public static Date getShutoffTime(AlarmEvent event) {
        Date shutOffTime = DateUtil.plusMinuets(event.getTime(), event.getShutOff());
        return DateUtil.getNextTime(shutOffTime);
    }

    public static boolean isSnooze(AlarmEvent event) {
        return EventUtil.isSnooze(event) && !isShutoffCancelSnooze(event);
    }
}
