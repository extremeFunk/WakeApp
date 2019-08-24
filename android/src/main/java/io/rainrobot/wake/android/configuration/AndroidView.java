package io.rainrobot.wake.android.configuration;

import android.view.View;

import io.rainrobot.wake.app.AbstractController;
import io.rainrobot.wake.app.IView;
import io.rainrobot.wake.core.util.Log;

public abstract class AndroidView<T1 extends WakeActivity, T2 extends AbstractController> implements IView<T2> {

    protected WakeActivity activity;
    private ContextMgr contextMgr;
    private Class<T1> activityClass;
    protected T2 controller;
    private String startMsg = "";


    public AndroidView(ContextMgr contextMgr, Class<T1> activityClass) {
        this.contextMgr = contextMgr;
        this.activityClass = activityClass;
    }

    public void show(){
        contextMgr.startActivity(this, activityClass);
    }

    public void setActivity(WakeActivity activity) {
        this.activity = activity;
        initializeActivity();
    }

    protected abstract void initializeActivity();

    public void setController(T2 controller) {
        this.controller = controller;
    }

    protected View findViewById(int id) {
        return activity.findViewById(id);
    }

    public void showMsg(String string) {
        //on showing the activity will be null, and the message
        //will be provoked by the activity onCreate method
        if (activity == null) startMsg = string;
        else activity.showMsg(string);
    }

    @Override
    public void startThinkMode() {
        if(contextMgr.isThinkDialogShow()) {
            activity.showThinkDialog();
        }
    }

    @Override
    public void stopThinkMode() {
        if(contextMgr.isThinkDialogClose()) {
            activity.hideThinkDialog();
        }
    }

    public boolean isShowMsgOnStart() {
        if (startMsg == "") return false;
        else return true;
    }

    public String getStartMsg() {
        return startMsg;
    }
}
