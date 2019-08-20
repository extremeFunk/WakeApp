package io.rainrobot.wake.android.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

@Deprecated
public class MyBaseActivity extends AppCompatActivity {
    protected ApplicationMgr applicationMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationMgr = (ApplicationMgr)this.getApplicationContext();
        applicationMgr.setCurrentActivity(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        applicationMgr.setCurrentActivity(this);
    }
    @Override
    protected void onPause() {
//        clearReferences();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    private void clearReferences(){
        Context currActivity = applicationMgr.getCurrentActivity();
        if (this.equals(currActivity))
            applicationMgr.setCurrentActivity(null);
    }
}