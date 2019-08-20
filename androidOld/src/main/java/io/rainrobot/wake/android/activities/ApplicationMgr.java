package io.rainrobot.wake.android.activities;

import android.app.Activity;
import android.app.Application;

@Deprecated
public class ApplicationMgr extends Application {


    private Activity currentContext;

    public ApplicationMgr() {
        super();
//        currentContext = getContext();
    }

    public void setCurrentActivity(Activity context) {
        this.currentContext = context;
    }

    public Activity getCurrentActivity() {
        return currentContext;
    }
}
