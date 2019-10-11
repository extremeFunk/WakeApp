package io.rainrobot.wake.android.client;

import android.os.AsyncTask;

import java.util.function.Consumer;
import java.util.function.Supplier;

import io.rainrobot.wake.client.ASyncProvider;

public class AndroidASyncProvider implements ASyncProvider {


    private AsyncTask task;

    @Override
    public <T> void asyncCall(Supplier<T> supplier,
                              Consumer<T> consumer,
                              Consumer<Exception> exceptionHandler,
                              Runnable doLast) {
        task = new ConsumerTaskClass(supplier, consumer, exceptionHandler, doLast);
        task.execute();

    }


    @Override
    public void asyncCall(Runnable run,
                          Consumer<Exception> exceptionHandler,
                          Runnable doLast) {
        task = new RunTask (run, exceptionHandler, doLast);
        task.execute();
    }

    @Override
    public void asyncCall(Runnable run,
                          Runnable postRun,
                          Consumer<Exception> exceptionHandler,
                          Runnable doLast) {
        task = new RunAndPostRunTask(run, postRun, exceptionHandler, doLast);
        task.execute();
    }

    @Override
    public void cancel() {
        task.cancel(true);
    }
}

abstract class Task <T> extends AsyncTask<Object, Integer, T> {

    protected final Consumer<Exception> exceptionHandler;
    protected final Runnable doLast;

    protected Exception exception;

    public Task(Consumer<Exception> exceptionHandler,
                Runnable doLast) {
        this.exceptionHandler = exceptionHandler;
        this.doLast = doLast;
    }
}

class ConsumerTaskClass <T> extends Task<T> {
    private Supplier<T> supplier;
    private Consumer<T> consumer;

    public ConsumerTaskClass(Supplier<T> supplier,
                             Consumer<T> consumer,
                             Consumer<Exception> exceptionHandler,
                             Runnable doLast) {
        super(exceptionHandler, doLast);
        this.supplier = supplier;
        this.consumer = consumer;
    }

    @Override
    protected T doInBackground(Object... prm) {
        try {
            return supplier.get();
        } catch (Exception e) {
            exception = e;
//            this.cancel(true);
            return null;
        }
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (exception != null) {
            exceptionHandler.accept(exception);
        }
        doLast.run();
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        if (exception == null) {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                exception = e;
            }
        }
        if(exception != null) exceptionHandler.accept(exception);
        doLast.run();
    }
}

class RunTask <T> extends Task<T> {
    private final Runnable run;

    public RunTask(Runnable run, Consumer<Exception> exceptionHandler, Runnable doLast) {
        super(exceptionHandler, doLast);
        this.run = run;
    }

    @Override
    protected T doInBackground(Object... objects) {
        try {run.run();}
        catch (Exception e) { exception = e; }
        return null;
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        if(exception != null) exceptionHandler.accept(exception);
        doLast.run();
    }
}

class RunAndPostRunTask <T> extends Task<T> {
    private final Runnable run;
    private final Runnable postRun;

    public RunAndPostRunTask(Runnable run,
                             Runnable postRun,
                             Consumer<Exception> exceptionHandler,
                             Runnable doLast) {

        super(exceptionHandler, doLast);
        this.postRun = postRun;
        this.run = run;
    }

    @Override
    protected T doInBackground(Object... objects) {
        try {run.run();}
        catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        if (exception == null) {
            try { postRun.run(); }
            catch (Exception e){ exception = e; }}
        if(exception != null) exceptionHandler.accept(exception);
        doLast.run();
    }
}


