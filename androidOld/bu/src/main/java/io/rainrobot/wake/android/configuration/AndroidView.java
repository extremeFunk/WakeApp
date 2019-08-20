package io.rainrobot.wake.android.configuration;

import android.view.View;

import io.rainrobot.wake.app.AbstractController;
import io.rainrobot.wake.app.IView;

public abstract class AndroidView<T1 extends WakeActivity, T2 extends AbstractController> implements IView<T2> {

    protected WakeActivity activity;
    private ContextMgr contextMgr;
    private Class<T1> activityClass;
    protected T2 controller;



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

    public void setMsg(String string) {
        activity.showMsg(string);
    }

    @Override
    public void startThinkMode() {
        activity.showBlankDialog();
    }

    @Override
    public void stopThinkMode() {
        activity.hideDialog();
    }
}
