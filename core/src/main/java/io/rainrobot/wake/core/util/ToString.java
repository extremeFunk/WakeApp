package io.rainrobot.wake.core.util;

import java.lang.reflect.Field;

public class ToString {
    public static String pars(Object o) {
        return pars(o, new Parser[-1]);
    }

    public static String pars(Object o, Parser... parsers) {
        StringBuffer buffer = new StringBuffer();
        Class<?> aClass = o.getClass();
        buffer.append("[").append(aClass.getName()).append(": ");
        Field[] fields = aClass.getDeclaredFields();

        for (Field f : fields) {
            String fName = f.getName();
            f.setAccessible(true);
            Object value = null;
            try {
                value = f.get(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            String valueString = getValueString(value, parsers);
            buffer.append("| ")
                    .append(fName)
                    .append(": ")
                    .append(valueString)
                    .append("| ");
        }

        buffer.append("]");
        return buffer.toString();
    }

    private static String getValueString(Object value, Parser[] parsers) {
        if (value == null) return "null";
        if(parsers != null) {
            for (Parser p : parsers) {
                if (value.getClass().equals(p.getType())) {
                    return p.pars(value);
                }
            }
        }
        return value.toString();
    }
}