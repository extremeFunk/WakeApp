package io.rainrobot.wake.android.log;

import android.util.Log;
import io.rainrobot.wake.core.util.ILog;

public class AndroidLog implements ILog {

    @Override
    public void i(String tag, String msg) {
        Log.i(tag,msg);
    }

    @Override
    public void d(String tag, String msg) {
        Log.d(tag,msg);
    }

    @Override
    public void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    @Override
    public void e(String tag, StackTraceElement[] stackTrace) {
        for(StackTraceElement s : stackTrace) {
            Log.e(tag, s.toString());
        }
    }


}
