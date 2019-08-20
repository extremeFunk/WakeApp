package io.rainrobot.wake.android.configuration;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import io.rainrobot.wake.alarm.IAlarmMgr;
import io.rainrobot.wake.alarm.ISchedulerServiceMgr;
import io.rainrobot.wake.android.alarm.AndroidAlarmMgr;
import io.rainrobot.wake.android.alarm.AndroidSchegualerServiceServiceMgr;
import io.rainrobot.wake.android.eventdatabase.AlarmRoomDao;
import io.rainrobot.wake.android.eventdatabase.AndroidRoomDatabase;
import io.rainrobot.wake.app.AppConfiguration;
import io.rainrobot.wake.app.AppContainer;
import io.rainrobot.wake.app.IDeviceDoa;
import io.rainrobot.wake.app.IEventDoa;
import io.rainrobot.wake.app.IRememberMeDoa;
import io.rainrobot.wake.app.ITokenDoa;
import io.rainrobot.wake.app.IViewFactory;
import io.rainrobot.wake.android.log.AndroidLog;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.android.client.AndroidASyncProvider;
import io.rainrobot.wake.client.IHttpRequestSender;
import io.rainrobot.wake.android.client.springClient.SpringHttpRequestSenderConfiguraton;
import io.rainrobot.wake.core.util.ILog;
import io.rainrobot.wake.core.util.Singleton;

public class AndroidAppConfiguration extends AppConfiguration {

    public static final String WAKEAPP_ANDROID_DB = "wakeapp-android-db";

    private SharedPreferences pref;
    private WakeApplication application;
    private Singleton<AndroidRoomDatabase> roomDatabase = new Singleton<>();


    public AndroidAppConfiguration(WakeApplication application) {
        this(new AppContainer(), application);
    }

    public AndroidAppConfiguration(AppContainer appContainer,
                                   WakeApplication application) {
        super(appContainer);
        this.application = application;
        this.pref = application.getPref();
    }


    @Override
    public ILog getLog() {
       return new AndroidLog();
    }

    @Override
    public IHttpRequestSender getHttpSender() {
        return appContainer.getHttpSender(
                () -> new SpringHttpRequestSenderConfiguraton()
                        .getRequestSender());
    }


    @Override
    public ITokenDoa getTokenDoa() {
        return appContainer.getDoaToken(() -> new AndroidTokenDoa(this.pref));
    }

    @Override
    public ISchedulerServiceMgr getAlarmSchedulerMgr() {
        return appContainer.alarmSchedulerServiceMgr.get(() -> {
           return new AndroidSchegualerServiceServiceMgr(getContaxt());
        });
    }

    private Context getContaxt() {
        return application.getApplicationContext();
    }

    @Override
    public ASyncProvider getAsyncProvider() {
        return new AndroidASyncProvider();
    }

    @Override
    public IEventDoa getEventDoa() {
        return appContainer.getEventDoa(() ->  {
            AndroidRoomDatabase db = getRoomDatabase();
            AlarmRoomDao roomDao = db.alarmRoomDao();
            return new AndroidEventDoa(roomDao);
        });
    }

    private AndroidRoomDatabase getRoomDatabase() {
        return roomDatabase.get(() -> {
            return Room.databaseBuilder(getContaxt(),
                    AndroidRoomDatabase.class, WAKEAPP_ANDROID_DB).
                    fallbackToDestructiveMigration()
                    .build();
        });
    }


    @Override
    public IAlarmMgr getAlarmMgr() {
        return appContainer.getAlarmMgr(
                () -> new AndroidAlarmMgr(getContaxt())
        );
    }


    @Override
    public IRememberMeDoa getRememberMeDoa() {
        return appContainer.getRemeberMeDoa(() -> new AndroidRememberMeDoa(pref));
    }
    @Override
    public IViewFactory getViewFactory() {
        return appContainer.getViewFactory(() -> {
            if (application == null) {
                throw new NullPointerException("WakeApplication is null - " +
                        "Cannot instantiate android view container ");
            }
            return new AndroidViewFactory(application.getContextMgr());
        });
    }

    @Override
    public IDeviceDoa getDeviceDoa() {
        return appContainer.getDviceDoa(() -> new AndroidDeviceDoa(this.pref));
    }

}
