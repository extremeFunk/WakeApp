package io.rainrobot.wake.android.configuration;

import io.rainrobot.wake.app.App;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class AndroidAppRunner {

    public static void run(WakeApplication wakeApplication) {
        CompletableFuture
                .supplyAsync(getAppASync(wakeApplication))
                .thenAccept((app) -> app.start());
    }

    private static Supplier<App> getAppASync(WakeApplication wakeApplication) {
        return () -> new AndroidAppConfiguration(wakeApplication)
                    .getApp();
    }



}
