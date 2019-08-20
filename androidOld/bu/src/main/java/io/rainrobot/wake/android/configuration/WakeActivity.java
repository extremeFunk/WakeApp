package io.rainrobot.wake.android.configuration;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.rainrobot.wake.android.activities.R;


public abstract class WakeActivity extends AppCompatActivity {

    protected final static int NO_VIEW_CONTENT_ID = -1;

    private WakeApplication app;
    private AlertDialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        app = getWakeApplication();
        if (app.getContextMgr() == null) {
            app.setContextMgr(new ContextMgr(this));
        }
        else {
            app.setActivity(this);
        }
    }

    @Override
    public void setContentView(int id) {
        if (id != NO_VIEW_CONTENT_ID) {
            super.setContentView(id);
        }
    }

    public WakeApplication getWakeApplication() {
        return (WakeApplication)getApplication();
    }

    public ContextMgr getContextMgr() {
        return getWakeApplication().getContextMgr();
    }


    protected abstract int getLayoutId();

    public void showMsg(String message) {
        hideThinkDialog();
        dialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(true)
                .create();
        dialog.show();
    }

    public void showBlankDialog() {
        hideThinkDialog();
        dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(R.layout.splash_screen)
                .create();

        dialog.show();
    }

    public void hideDialog() {
        if (dialog != null && dialog.isShowing()) dialog.hide();
    }

}
