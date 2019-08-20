package io.rainrobot.wake.app;

import io.rainrobot.wake.client.ASyncProvider;

public abstract class ControllerWithId<T1 extends IView, T2 extends IModel> extends AbstractController<T1, T2> {

    protected int controllerId;

    public ControllerWithId(T1 view,
                            T2 model,
                            ControllerMgr controllerMgr,
                            ASyncProvider aSyncProvider) {
        super(view, model, controllerMgr, aSyncProvider);
    }

    public void show(int id) {
        this.controllerId = id;
        view.show();
    }
}
