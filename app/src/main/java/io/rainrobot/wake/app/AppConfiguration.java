package io.rainrobot.wake.app;

import io.rainrobot.wake.alarm.AlarmUpdater;
import io.rainrobot.wake.alarm.IAlarmMgr;
import io.rainrobot.wake.alarm.ISchedulerServiceMgr;
import io.rainrobot.wake.app.log.MyLogFile;
import io.rainrobot.wake.client.AccountClient;
import io.rainrobot.wake.client.ClientFactory;
import io.rainrobot.wake.client.LoginClient;
import io.rainrobot.wake.client.TestTokenClient;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.client.IHttpRequestSender;
import io.rainrobot.wake.core.util.ILog;
import io.rainrobot.wake.core.util.Log;

public abstract class AppConfiguration {

    protected AppContainer appContainer;

    public abstract IHttpRequestSender getHttpSender();

    public abstract IAlarmMgr getAlarmMgr();

    public abstract IRememberMeDoa getRememberMeDoa();

    public abstract IViewFactory getViewFactory();

    public abstract IDeviceDoa getDeviceDoa();

    public abstract IEventDoa getEventDoa();

    public abstract ILog getLog();

    public AppConfiguration(AppContainer appContainer) {
        this.appContainer = appContainer;
        Log.log = getLog();
    }

    public LoginMgr getLoginMgr() {
        return appContainer.getLoginMgr(
                () -> {
                    TokenMgr tokenMgr = getTokenMgr();
                    LoginClient loginClient = getClientFactory().getLoginClient();
                    IRememberMeDoa rememberMeDoa = getRememberMeDoa();
                    DeviceInfoMgr deviceInfoMgr = getDeviceInfoMgr();
                    AccountClient accountClient = getClientFactory().getAccountClient();

                    return new LoginMgr(tokenMgr,
                            loginClient,
                            deviceInfoMgr,
                            rememberMeDoa, accountClient);
                });
    }

    public ClientFactory getClientFactory() {
        return appContainer.getClientFactory(() -> new ClientFactory(getHttpSender()));
    }

    public DeviceInfoMgr getDeviceInfoMgr() {
        return appContainer.getDeviceInfoMgr(() -> {
            return new DeviceInfoMgr(getDeviceDoa(),
                    getEventDoa(),
                    getRememberMeDoa(),
                    getTokenDoa());
        });
    }

    public abstract ITokenDoa getTokenDoa();

    public TokenMgr getTokenMgr() {
        return appContainer.getTokenMgr(() -> {
            TestTokenClient client = getClientFactory().getTestTokenClient();
            return new TokenMgr(getTokenDoa(), getHttpSender(), client);
        });
    }

    public ControllerMgr getControllerMgr() {
        return appContainer.getControllerMgrHolder(
                () -> ControllerMgr.create(getControllerFactory(), getLogger()));
    }


    public App getApp() {
        return appContainer.getApp(() -> new App(getControllerMgr(),
                                                    getLoginMgr(),
                                                    getAsyncProvider(),
                                                    getDeviceInfoMgr(),
                                                    getLogger()));
    }

    public AppLogger getLogger() {
        return appContainer.logger.get(() -> new AppLogger.Builder()
                                                .setAlarmScheduler(getAlarmSchedulerMgr())
                //null prevent dependency loop - this is set at getControllerMgr
                .setControllerMgr(null)
                .setdMgr(getDeviceInfoMgr())
                .setEventUpdateMgr(getEventUpdateService())
                .setRmDoa(getRememberMeDoa())
                .settMgr(getTokenMgr())
                .build());
    }

    public abstract ISchedulerServiceMgr getAlarmSchedulerMgr();


    public EventUpdateMgr getEventUpdateService() {
        return appContainer.getEventUpdateService(() ->
            new EventUpdateMgr.Builder()
                    .setDeviceClient(getClientFactory().getDeviceClient())
                    .setAccountClient(getClientFactory().getAccountClient())
                    .setPresetClient(getClientFactory().getPresetClient())
                    .setAlarmUpdater(getAlarmUpdater())
                    .setDeviceInfoMgr(getDeviceInfoMgr())
                    .setLoginMgr(getLoginMgr())
                    .build()
        );
    }

    public AlarmUpdater getAlarmUpdater() {
        return appContainer.getAlarmUpdater(
                () -> new AlarmUpdater(getAlarmMgr(), getEventDoa())
        );
    }

    public ControllerFactory getControllerFactory() {
        return appContainer.getControllerFactory(
                () -> new ControllerFactory(getViewFactory(),
                                            getModelFactory(),
                                            getAsyncProvider(), getLogger()));
    }

    public abstract ASyncProvider getAsyncProvider();


    public ModelFactory getModelFactory() {
        return appContainer.getModelFactory(() ->
            new ModelFactory(getClientFactory(),
                                getDeviceInfoMgr(),
                                getLoginMgr(), getEventUpdateService())
        );
    }
}
