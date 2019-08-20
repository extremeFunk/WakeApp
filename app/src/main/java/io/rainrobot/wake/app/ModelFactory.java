package io.rainrobot.wake.app;

import io.rainrobot.wake.client.*;
import io.rainrobot.wake.model.*;

public class ModelFactory {

    private ClientFactory clientFactory;
    private DeviceInfoMgr deviceInfoMgr;
    private LoginMgr loginMgr;
    private EventUpdateMgr eventUpdateMgr;

    public ModelFactory(ClientFactory clientFactory, DeviceInfoMgr deviceInfoMgr, LoginMgr loginMgr, EventUpdateMgr eventUpdateMgr) {
        this.clientFactory = clientFactory;
        this.deviceInfoMgr = deviceInfoMgr;
        this.loginMgr = loginMgr;
        this.eventUpdateMgr = eventUpdateMgr;
    }

    public LoginModel getLogin() {
        return new LoginModel(loginMgr);
    }

    public MainMenuModel getMainMenu() {
        return new MainMenuModel(loginMgr);
    }

    public SighupModel getSignup() {
        SignupClient client = clientFactory.getSignupClient();
        return new SighupModel(client, loginMgr, deviceInfoMgr);
    }

    public PresetsModel getPresets() {
        PresetClient client = clientFactory.getPresetClient();
        return new PresetsModel(client, eventUpdateMgr);
    }

    public SettingsModel getSettings() {
        DeviceClient client = clientFactory.getDeviceClient();
        AccountClient accountClient = clientFactory.getAccountClient();
        return new SettingsModel(accountClient, client, deviceInfoMgr);
    }

    public DeviceEventsModel getDeviceEvents() {
        return new DeviceEventsModel(deviceInfoMgr);
    }

    public EventListModel getEventList() {
        DeviceClient deviceClient = clientFactory.getDeviceClient();
        PresetClient presetClient = clientFactory.getPresetClient();
        AlarmEventClient eventClient = clientFactory.getEventClient();
        return new EventListModel(eventClient, presetClient, deviceClient, eventUpdateMgr);
    }

    public DeviceRegModel getDeviceReg() {
        DeviceClient client = clientFactory.getDeviceClient();
        AccountClient accountClient = clientFactory.getAccountClient();
        return new DeviceRegModel(client, accountClient, deviceInfoMgr);
    }

    public SelectDeviceModel getSelectDevice() {
        DeviceClient client = clientFactory.getDeviceClient();
        AccountClient accountClient = clientFactory.getAccountClient();
        return new SelectDeviceModel(deviceInfoMgr, client, accountClient);
    }

    public IsNewDeviceModel getIsNewDevice() {
        return new IsNewDeviceModel();
    }

    public EnterNewPasswordModel getEnterNewPassword() {
        ResetPasswordClient client = clientFactory.getResetPasswordClient();
        return new EnterNewPasswordModel(client);
    }

    public EnterEmailModel getEnterEmailModel() {
        ResetPasswordClient client = clientFactory.getResetPasswordClient();
        return new EnterEmailModel(client);
    }
}
