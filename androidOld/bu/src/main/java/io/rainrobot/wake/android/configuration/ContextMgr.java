package io.rainrobot.wake.android.configuration;

import android.content.Context;
import android.content.Intent;

public class ContextMgr implements ContextProvider{
    private WakeActivity context;
    private AndroidView view;


    public ContextMgr(WakeActivity contxt) {
        setContext(contxt);
    }

    public void setContext(WakeActivity context) {
        this.context = context;
        if (view != null) {
            view.setActivity(context);
        }
    }

    public <T extends WakeActivity> void startActivity(AndroidView view, Class<T> clas) {
        this.view = view;
        Intent intent = new Intent(context, clas);
        context.startActivity(intent);
    }
    @Override
    public Context getContext() {
        return context.getApplicationContext();
    }
}
