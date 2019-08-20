package io.rainrobot.wake.controller;

import io.rainrobot.wake.app.AppLogger;
import io.rainrobot.wake.app.Controller;
import io.rainrobot.wake.app.ControllerMgr;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.model.LoginModel;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.LoginView;

public class LoginController extends Controller<LoginView, LoginModel> {

    public static final String FAIL_MSG = "Username or password are incorrect";
    private final AppLogger logger;

    public LoginController(LoginView view,
                           LoginModel model,
                           AppLogger logger,
                           ControllerMgr controllerMgr,
                           ASyncProvider aSyncProvider) {

        super(view, model, controllerMgr, aSyncProvider);
        this.logger = logger;
    }

    public Command getSingupCommand() {
        return controllerMgr::showSingup;
    }

    public Command getForgotPasswordCommand() { return () -> controllerMgr.showEnterMail(); }

    public Command getLoginCommand() {
        return () -> asyncCall(
                (() -> model.login(view.getUsernameField(),
                        view.getPasswordField(),
                        view.isRememberMe())),
                ((flag) -> {
                    if(flag == LoginModel.SUCCESS) onLoginSuccess();
                    else view.showMsg(FAIL_MSG);
                }));
    }

    private void onLoginSuccess() {
        asyncCall(
                (model::isDeviceRegister),
                (isRegister) -> {
                    if (isRegister) { asyncCall(logger::login); }
                    else controllerMgr.showIsNewDevice();
                });
    }
}


