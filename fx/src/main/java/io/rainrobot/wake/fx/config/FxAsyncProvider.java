package io.rainrobot.wake.fx.config;

import io.rainrobot.wake.client.ASyncProvider;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Service;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FxAsyncProvider implements ASyncProvider {

    ASyndService service = new ASyndService();

    @Override
    public <T> void asyncCall(Supplier<T> supplier,
                              Consumer<T> consumer,
                              Consumer<Exception> exceptionHandler,
                              Runnable doLast) {
        Task task = new SuplierTask(supplier);

        onTaskFail(exceptionHandler, task, doLast);

        task.setOnSucceeded((event -> {
            Platform.runLater(() -> {
                consumer.accept((T) task.getValue());
                doLast.run();
            });
        }));

        service.setTask(task);
        service.restart();
    }

    private void onTaskFail(Consumer<Exception> exceptionHandler, Task task, Runnable doLast) {
        task.setOnFailed((e) -> {
            Platform.runLater(() ->  {
                exceptionHandler.accept((Exception) task.getException());
                doLast.run();
            });
        });
    }


    @Override
    public void asyncCall(Runnable run,
                          Runnable postRun,
                          Consumer<Exception> exceptionHandler,
                          Runnable doLast) {
        //this extra layer insure that if an exception is thrown
        //the post run will not be executed
        Supplier sup = () -> {
          run.run();
          return true;
        };
        Consumer con = (flag) -> {
            postRun.run();
        };
        asyncCall(sup, con, exceptionHandler, doLast);
    }

    @Override
    public void asyncCall(Runnable run,
                          Consumer<Exception> exceptionHandler,
                          Runnable doLast) {
        Task task = new RunTask(run, exceptionHandler);

        onTaskFail(exceptionHandler, task, doLast);

        task.setOnSucceeded((e) -> {
            Platform.runLater(() -> { doLast.run(); });
        });

        service.setTask(task);
        service.restart();
    }

    @Override
    public void cancel() {
        service.cancel();
    }
}

class ASyndService<T> extends Service<T> {

    private Task<T> task;

    public void setTask(Task<T> task) {
        this.task = task;
    }

    @Override
    protected Task<T> createTask() {
        return task;
    }
}

class SuplierTask<T> extends Task<T> {

    private Supplier<T> supplier;

    SuplierTask(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    protected T call() throws Exception {
        return supplier.get();
    }
}

class RunTask extends Task {
    private final Runnable runnable;
    private final Consumer<Exception> exceptionHandler;

    public RunTask(Runnable runnable, Consumer<Exception> exceptionHandler) {
        this.runnable = runnable;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    protected Object call() throws Exception {
        Platform.runLater(() -> execute());
        return null;
    }

    private void execute() {
        try {
            runnable.run();
        } catch (Exception e) {
            exceptionHandler.accept(e);
        }
    }
}
