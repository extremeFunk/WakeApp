package io.rainrobot.wake.client;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface ASyncProvider {

    <T> void asyncCall(Supplier<T> supplier,
                       Consumer<T> consumer,
                       Consumer<Exception> exceptionHandler,
                       Runnable doLast);

    void asyncCall(Runnable run,
                   Consumer<Exception> exceptionHandler,
                   Runnable doLast);

    void asyncCall(Runnable run,
                   Runnable postRun,
                   Consumer<Exception> exceptionHandler,
                   Runnable doLast);

    void cancel();
}
