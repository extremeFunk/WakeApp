package io.rainrobot.wake.core.util;

public class defaultLog implements ILog {
    @Override
    public void i(String tag, String msg) {
        print(tag, msg);
    }

    private void print(String tag, String msg) {
        System.out.print(tag + " " + msg);
    }

    @Override
    public void d(String tag, String msg) {
        print(tag, msg);
    }

    @Override
    public void e(String tag, String msg) {
        print(tag, msg);
    }

    @Override
    public void e(String tag, StackTraceElement[] stackTrace) {
        print(tag, "");
        for (StackTraceElement s : stackTrace) {
            print("", s.toString());
        }
    }
}
