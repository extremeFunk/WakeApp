package io.rainrobot.wake.app;


import io.rainrobot.wake.alarm.ISchedulerServiceMgr;
import io.rainrobot.wake.core.util.Log;

import java.util.concurrent.CompletableFuture;

public class AppLogger {

    private TokenMgr tMgr;
    private DeviceInfoMgr dMgr;
    private ISchedulerServiceMgr alarmScheduler;
    private EventUpdateMgr eventUpdateMgr;
    private ControllerMgr controllerMgr;

    private IRememberMeDoa rmDoa;

    private AppLogger(Builder builder) {
        this.tMgr = builder.tMgr;
        this.dMgr = builder.dMgr;
        this.alarmScheduler = builder.alarmScheduler;
        this.eventUpdateMgr = builder.eventUpdateMgr;
        this.controllerMgr = builder.controllerMgr;
        this.rmDoa = builder.rmDoa;
    }

    public void login() {
        try {
            if(!dMgr.isSchedulerOn()) {
                alarmScheduler.turnOn();
                dMgr.setSchedulerOn(true);
            }
            CompletableFuture.runAsync(
                    () -> eventUpdateMgr.updateEvents());
            //need to have a async provider
            controllerMgr.showMainMenu();
        } catch (Exception e) {
            Log.err("Logger", e);
            controllerMgr.showLogin();
        }

    }

    public void logout() {
        tMgr.removeToken();
        alarmScheduler.turnOff();
        dMgr.setSchedulerOn(false);
        eventUpdateMgr.clearEvents();
        dMgr.setUserName("");
        rmDoa.set(false);
        //need to have a async provider
        //controllerMgr.showLogin();
    }

    public void setControllerMgr(ControllerMgr controllerMgr) {
        this.controllerMgr = controllerMgr;
    }

    public static class Builder {

        private TokenMgr tMgr;
        private DeviceInfoMgr dMgr;
        private ISchedulerServiceMgr alarmScheduler;
        private EventUpdateMgr eventUpdateMgr;
        private ControllerMgr controllerMgr;
        private IRememberMeDoa rmDoa;

        public AppLogger build() {
            return new AppLogger(this);
        }

        public Builder settMgr(TokenMgr tMgr) {
            this.tMgr = tMgr;
            return this;
        }

        public Builder setdMgr(DeviceInfoMgr dMgr) {
            this.dMgr = dMgr;
            return this;
        }

        public Builder setAlarmScheduler(ISchedulerServiceMgr alarmScheduler) {
            this.alarmScheduler = alarmScheduler;
            return this;
        }

        public Builder setEventUpdateMgr(EventUpdateMgr eventUpdateMgr) {
            this.eventUpdateMgr = eventUpdateMgr;
            return this;
        }

        public Builder setControllerMgr(ControllerMgr controllerMgr) {
            this.controllerMgr = controllerMgr;
            return this;
        }

        public Builder setRmDoa(IRememberMeDoa rmDoa) {
            this.rmDoa = rmDoa;
            return this;
        }
    }
}
