package io.rainrobot.wake.android.configuration;

import android.app.Application;
import android.content.SharedPreferences;

import io.rainrobot.wake.android.activities.R;

public class WakeApplication extends Application {

    private ContextMgr contextMgr;

    public void setContextMgr(ContextMgr contextMgr) {
        this.contextMgr = contextMgr;
    }

    public ContextMgr getContextMgr() {
        return contextMgr;
    }

    public void setActivity(WakeActivity activity) {
        if (contextMgr != null) {
            this.contextMgr.setContext(activity);
        }
    }

    public SharedPreferences getPref() {
        String prefString = getString(R.string.preferences);
        return getSharedPreferences(prefString, MODE_PRIVATE);
    }
}
