package io.rainrobot.wake.android.configuration;

public class AndroidAppRunner {

//    public static void run(ContextMgr contextMgr, SharedPreferences pref) {
//        AppContainer container = new AppContainer();
//        runWith(contextMgr, pref, container);
//    }
//
//    public static void runWith(ContextMgr contextMgr,
//                               SharedPreferences pref,
//                               AppContainer container) {
//        AndroidAppConfiguration config
//                = new AndroidAppConfiguration(container, pref, contextMgr);
//        config.getApp().start();
//    }

    public static void run(WakeApplication wakeApplication) {
        new AndroidAppConfiguration(wakeApplication)
                .getApp()
                .start();
    }


}
