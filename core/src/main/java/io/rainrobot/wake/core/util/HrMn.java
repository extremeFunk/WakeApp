package io.rainrobot.wake.core.util;

import java.util.Calendar;
import java.util.Date;

public class HrMn {
    private final int hr;
    private final int mn;

    public HrMn(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        hr = cal.get(Calendar.HOUR_OF_DAY);
        mn  = cal.get(Calendar.MINUTE);
    }

    public int getHr() {
        return hr;
    }
    public int getMn() {
        return mn;
    }
}
