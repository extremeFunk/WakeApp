package io.rainrobot.wake.core.util;

public class Log {
    private static final String PREFIX = "WakeLog ";
    private static final String SP = " -> ";
    private static final String DEBUG = PREFIX + "Debug" + SP;
    private static final String INFO = PREFIX + "Info" + SP;
    private static final String ERORR = PREFIX + "Erorr" + SP;
    private static final String BALASH = "Balash" + SP;
    private static final String ROOT = SP + "Root: ";
    private static final String MSG = SP + "Message: ";
    private static final String TAG = SP + "Tag: ";
    public static ILog log;

    public static void err(String tag, Throwable e) {
        tag = ERORR + tag;
        log.e(tag, "Exception message: " + e.getMessage());
        log.e(tag, "Stack trace:");
        log.e(tag, e.getStackTrace());
    }
    public static void err(Object root, Throwable e) {
        String tag = ERORR + root.getClass().getSimpleName();
        log.e(tag, "Exception message: " + e.getMessage());
        log.e(tag, "Stack trace:");
        log.e(tag, e.getStackTrace());
    }

    

    public static void d(Object root, String msg) {
        d(ROOT + root.getClass().getName(),  msg);
    }

    public static void d(Object root, String tag, String msg) {
        d(ROOT + root.getClass().getName() + tag,  msg);
    }

    public static void i(String msg) {
        log.i(INFO, MSG + msg);
    }

    private static void d(String tag, String msg) {
        log.d(DEBUG + TAG + tag, MSG + msg);
    }

    public static void err(String tag, String msg, Throwable e) {
        log.e(ERORR + tag, MSG + msg);
        err(tag, e);
    }
}
