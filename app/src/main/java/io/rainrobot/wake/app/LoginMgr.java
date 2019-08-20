package io.rainrobot.wake.app;

import io.rainrobot.wake.client.AccountClient;
import io.rainrobot.wake.client.ResponseException;
import io.rainrobot.wake.client.LoginClient;
import io.rainrobot.wake.controller.LoginController;
import io.rainrobot.wake.core.LoginEntity;

public class LoginMgr {

    private TokenMgr tokenMgr;
    private LoginClient loginClient;
    private DeviceInfoMgr deviceInfoMgr;
    private IRememberMeDoa rememberMeDoa;
    private AccountClient accountClient;

    public LoginMgr(TokenMgr tokenMgr,
                    LoginClient loginClient,
                    DeviceInfoMgr deviceInfoMgr,
                    IRememberMeDoa rememberMeDoa,
                    AccountClient accountClient) {

        this.tokenMgr = tokenMgr;
        this.loginClient = loginClient;
        this.deviceInfoMgr = deviceInfoMgr;
        this.rememberMeDoa = rememberMeDoa;
        this.accountClient = accountClient;
    }


    public String loginUserPass(String username, String password,
                                boolean rememberMe) throws FailedLoginException {
        String token = null;
        try {
            token = loginClient.login(new LoginEntity(username, password));
        } catch (ResponseException e) {
            if(e.getStatus() == 401) {
                throw new FailedLoginException(LoginController.FAIL_MSG);
            }
            else throw e;
        }
        deviceInfoMgr.setUserName(username);
        tokenMgr.setToken(token);
        deviceInfoMgr.setLastEventsChange(accountClient.getLastChange());
        rememberMeDoa.set(rememberMe);

        return token;

    }


    public boolean isRememberMe() {
        return rememberMeDoa.isOn();
    }

    public boolean isDeviceRegister() {
        return deviceInfoMgr.isDeviceRegister();
    }

    public Boolean isTokenValid() {
        return tokenMgr.testToken(deviceInfoMgr.getUserName());
    }

    public void logout() {
        tokenMgr.removeToken();
        rememberMeDoa.set(false);
    }

    public static class FailedLoginException extends IllegalStateException{
        public FailedLoginException(String string) {
            super(string);
        }
    }
}


