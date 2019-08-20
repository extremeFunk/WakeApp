package io.rainrobot.wake.app;

import java.util.function.Supplier;

import io.rainrobot.wake.alarm.AlarmUpdater;
import io.rainrobot.wake.alarm.IAlarmMgr;
import io.rainrobot.wake.alarm.ISchedulerServiceMgr;
import io.rainrobot.wake.app.log.MyLogFile;
import io.rainrobot.wake.client.ClientFactory;
import io.rainrobot.wake.client.IHttpRequestSender;
import io.rainrobot.wake.core.util.Singleton;

public class AppContainer {

    public Singleton<AppLogger> logger = new Singleton<>();
    public Singleton<ISchedulerServiceMgr> alarmSchedulerServiceMgr = new Singleton<>();
    public Singleton<DeviceInfoMgr> deviceInfoMgr = new Singleton<>();
    public Singleton<TokenMgr> tokenMgr = new Singleton<>();
    public Singleton<ControllerMgr> controllerMgr = new Singleton<>();
    public Singleton<LoginMgr> loginMgr = new Singleton<>();
    public Singleton<App> app = new Singleton<>();
    public Singleton<IRememberMeDoa> remeberMeDoa = new Singleton<>();
    public Singleton<ControllerFactory> controllerFactory = new Singleton<>();
    public Singleton<ClientFactory> clientFactory = new Singleton<>();
    public Singleton<IHttpRequestSender> sender = new Singleton<>();
    public Singleton<IViewFactory> viewFactory = new Singleton<>();
    public Singleton<ModelFactory> modelFactory = new Singleton<>();
    public Singleton<ITokenDoa> tokenDoa = new Singleton<>();
    public Singleton<EventUpdateMgr> eventUpdateService = new Singleton<>();
    public Singleton<IEventDoa> eventDoa = new Singleton<>();
    public Singleton<IAlarmMgr> alarmMgr = new Singleton<>();
    public Singleton<IDeviceDoa> deviceDoa = new Singleton<>();
    public Singleton<AlarmUpdater> alarmUpdater = new Singleton<>();

    public AppContainer() {

    }

    public IEventDoa getEventDoa(Supplier<IEventDoa> builder) {
        return this.eventDoa.get(builder);
    }

    public void setEventDoa(IEventDoa eventDoa) {
        this.eventDoa.set(eventDoa);
    }

    public void setDeviceInfoMgr(DeviceInfoMgr deviceInfoMgr) {
        this.deviceInfoMgr.set(deviceInfoMgr);
    }

    public void setTokenMgr(TokenMgr tokenMgr) {
        this.tokenMgr.set(tokenMgr);
    }

    public void setControllerMgr(ControllerMgr controllerMgr) {
        this.controllerMgr.set(controllerMgr);
    }

    public void setLoginMgr(LoginMgr loginMgr) {
        this.loginMgr.set(loginMgr);
    }

    public void setApp(App app) {
        this.app.set(app);
    }

    public void setRemeberMeDoa(IRememberMeDoa remeberMeDoa) {
        this.remeberMeDoa.set(remeberMeDoa);
    }

    public void setControllerFactory(ControllerFactory controllerFactory) {
        this.controllerFactory.set(controllerFactory);
    }

    public void setClientFactory(ClientFactory clientFactory) {
        this.clientFactory.set(clientFactory);
    }

    public void setSender(IHttpRequestSender sender) {
        this.sender.set(sender);
    }

    public void setViewFactory(IViewFactory viewFactory) {
        this.viewFactory.set(viewFactory);
    }

    public void setModelFactory(ModelFactory modelFactory) {
        this.modelFactory.set(modelFactory);
    }

    public void setTokenDoa(ITokenDoa tokenDoa) {
        this.tokenDoa.set(tokenDoa);
    }

    public DeviceInfoMgr getDeviceInfoMgr(Supplier<DeviceInfoMgr> builder) {
        return deviceInfoMgr.get(builder);
    }

    public TokenMgr getTokenMgr(Supplier<TokenMgr> builder) {
        return tokenMgr.get(builder);
    }

    public LoginMgr getLoginMgr(Supplier<LoginMgr> builder) {
        return loginMgr.get(builder);
    }

    public App getApp(Supplier<App> builder) {
        return app.get(builder);
    }

    public IRememberMeDoa getRemeberMeDoa(Supplier<IRememberMeDoa> builder) {
        return remeberMeDoa.get(builder);
    }

    public ControllerFactory getControllerFactory(Supplier<ControllerFactory> builder) {
        return controllerFactory.get(builder);
    }

    public ClientFactory getClientFactory(Supplier <ClientFactory> builder) {
        return clientFactory.get(builder);
    }

    public IHttpRequestSender getHttpSender(Supplier<IHttpRequestSender> builder) {
        return sender.get(builder);
    }

    public IViewFactory getViewFactory(Supplier<IViewFactory> builder) {
        return viewFactory.get(builder);
    }


    public ModelFactory getModelFactory(Supplier<ModelFactory> builder) {
        return modelFactory.get(builder);
    }


    public ITokenDoa getDoaToken(Supplier<ITokenDoa> builder) {
        return tokenDoa.get(builder);
    }

    public ControllerMgr getControllerMgrHolder(Supplier<ControllerMgr> builder) {
        return controllerMgr.get(builder);
    }

    public EventUpdateMgr getEventUpdateService(Supplier<EventUpdateMgr> builder) {
        return eventUpdateService.get(builder);
    }

    public void setEventUpdateService(EventUpdateMgr eventUpdateMgr) {
        this.eventUpdateService.set(eventUpdateMgr);
    }


    public IAlarmMgr getAlarmMgr(Supplier<IAlarmMgr> builder) {
        return this.alarmMgr.get(builder);
    }

    public IDeviceDoa getDviceDoa(Supplier<IDeviceDoa> builder) {
        return this.deviceDoa.get(builder);
    }

    public AlarmUpdater getAlarmUpdater(Supplier<AlarmUpdater> supplier) {
        return this.alarmUpdater.get(supplier);
    }

    public void setDeviceDoa(IDeviceDoa deviceDoa) {
        this.deviceDoa.set(deviceDoa);
    }

}
