package io.rainrobot.wake.core.util;

public interface ILog {
    void i(String tag, String msg);

    void d(String tag, String msg);

    void e(String tag, String msg);

    void e(String tag, StackTraceElement[] stackTrace);
}
