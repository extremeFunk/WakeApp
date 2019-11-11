package io.rainrobot.wake.core.util;

import java.util.GregorianCalendar;

public class GregorianCalParser extends Parser {

    public GregorianCalParser() {
        super(GregorianCalendar.class);
    }

    @Override
    public String pars(Object o) {
        GregorianCalendar calendar = (GregorianCalendar) o;
        return String.valueOf(calendar.getTimeInMillis());
    }
}
