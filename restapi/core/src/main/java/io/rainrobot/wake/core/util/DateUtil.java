package io.rainrobot.wake.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtil {

    public static Date fromHrAndMn(int hr, int mn) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(GregorianCalendar.HOUR_OF_DAY, hr);
        calendar.set(GregorianCalendar.MINUTE, mn);
        return getNextTime(calendar.getTime());
    }

    public static String toHrMnTxt(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }

    public static Date fromMili(long timeInMili) {
        Date d = new Date();
        d.setTime(timeInMili);
        return d;
    }

    public static Date getNextTime(Date time) {
        Calendar now = Calendar.getInstance();
        Calendar ref = Calendar.getInstance();
        ref.setTime(time);
        ref.set(Calendar.YEAR, now.get(Calendar.YEAR));
        ref.set(Calendar.MONTH, now.get(Calendar.MONTH));
        ref.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
        if (now.after(ref)) { ref.add(Calendar.DAY_OF_MONTH, 1); }
        return ref.getTime();
    }

    public static Date plusMinuets(Date date, int add) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, add);
        return c.getTime();
    }

    public static int getMn(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    public static int getHr(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }
}
