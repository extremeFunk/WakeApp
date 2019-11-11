package io.rainrobot.wake.client;

import io.rainrobot.wake.core.util.Singleton;


public class ClientFactory {
    private final IHttpRequestSender sender;

    private Singleton<TestTokenClient> testTokenClient = new Singleton<>();
    private Singleton<LoginClient> loginClient = new Singleton<>();
    private Singleton<SignupClient> signupClient = new Singleton<>();
    private Singleton<PresetClient> presetClient = new Singleton<>();
    private Singleton<DeviceClient> deviceClient = new Singleton<>();
    private Singleton<AlarmEventClient> alarmEventClient = new Singleton<>();
    private Singleton<AccountClient> accountClient = new Singleton<>();
    private Singleton<ResetPasswordClient> ResetPasswordClient = new Singleton<>();

    public ClientFactory(IHttpRequestSender sender) {
        this.sender = sender;
    }

    public TestTokenClient getTestTokenClient() {
        return testTokenClient.get(
                ()-> new TestTokenClient(sender, AddressValues.tokenTestUrl()));
    }

    public LoginClient getLoginClient() {
        return loginClient.get(
                ()-> new LoginClient(sender, AddressValues.getLoginUrl()));
    }

    public SignupClient getSignupClient() {
        return signupClient.get(
                ()-> new SignupClient(sender, AddressValues.getSighupUrl()));
    }

    public PresetClient getPresetClient() {
        return presetClient.get(
                ()-> new PresetClient(sender, AddressValues.getPresetUrl()));
    }

    public DeviceClient getDeviceClient() {
        return deviceClient.get(
                ()-> new DeviceClient(sender, AddressValues.getDeviceUrl()));
    }

    public AlarmEventClient getEventClient() {
        return alarmEventClient.get(
                ()-> new AlarmEventClient(sender, AddressValues.getAlarmUrl()));
    }

    public AccountClient getAccountClient() {
        return accountClient.get(
                () -> new AccountClient(sender, AddressValues.getAccoutUrl()));
    }

    public ResetPasswordClient getResetPasswordClient() {
        return ResetPasswordClient.get(
                () -> new ResetPasswordClient(sender, AddressValues.getResetPasswordUrl()));
    }
}
