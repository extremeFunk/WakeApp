package io.rainrobot.wake.app;

import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.client.ResponseException;
import io.rainrobot.wake.core.util.Log;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractController<T1 extends IView, T2 extends IModel> {

    protected final T1 view;
    protected final T2 model;
    protected final ControllerMgr controllerMgr;
    private final ASyncProvider aSyncProvider;
    private final Consumer<Exception> exceptionHandler = new ControllerExceptionHandler();

    public AbstractController(T1 view,
                              T2 model,
                              ControllerMgr controllerMgr,
                              ASyncProvider aSyncProvider) {
        this.view = view;
        view.setController(this);
        this.model = model;
        this.controllerMgr = controllerMgr;
        this.aSyncProvider = aSyncProvider;
    }

    protected <T> void asyncCall(Supplier<T> supplier, Consumer<T> consumer) {
        view.startThinkMode();
        aSyncProvider.asyncCall(supplier, consumer, exceptionHandler,
                () -> view.stopThinkMode());
    }

    protected void asyncCall(Runnable run) {
        view.startThinkMode();
        aSyncProvider.asyncCall(run, exceptionHandler,
                () -> view.stopThinkMode());
    }

    protected void asyncCall(Runnable run, Runnable postRun) {
        view.startThinkMode();
        aSyncProvider.asyncCall(run, postRun, exceptionHandler,
                () -> view.stopThinkMode());
    }

    class ControllerExceptionHandler implements Consumer<Exception> {

        @Override
        public void accept(Exception e) {
            Log.err("async call thrown error", e);

            if(e instanceof ResponseException
                    || e instanceof LoginMgr.FailedLoginException
                    || e instanceof UserInputExeption){
                view.showMsg(e.getMessage());
            } else {
                view.showMsg("oops... something went wrong");
            }
        }
    }

}

