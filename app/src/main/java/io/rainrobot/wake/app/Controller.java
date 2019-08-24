package io.rainrobot.wake.app;

import io.rainrobot.wake.client.ASyncProvider;

public abstract class Controller<T1 extends IView, T2 extends IModel> extends AbstractController<T1, T2> {
    public Controller(T1 view, T2 model,
                      ControllerMgr controllerMgr,
                      ASyncProvider aSyncProvider) {
        super(view, model, controllerMgr, aSyncProvider);
    }

    public void show() {
        view.show();
    }

    public void showWithMsg(String string) {
        show();
        view.showMsg(string);
    }
}
