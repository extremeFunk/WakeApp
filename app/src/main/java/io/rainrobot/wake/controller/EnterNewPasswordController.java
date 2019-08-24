package io.rainrobot.wake.controller;

import io.rainrobot.wake.app.Controller;
import io.rainrobot.wake.app.ControllerMgr;
import io.rainrobot.wake.app.UserInputExeption;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.model.EnterNewPasswordModel;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.EnterNewPasswordView;

public class EnterNewPasswordController extends Controller<EnterNewPasswordView, EnterNewPasswordModel> {

    public EnterNewPasswordController(EnterNewPasswordView view,
                                      EnterNewPasswordModel model,
                                      ControllerMgr controllerMgr,
                                      ASyncProvider aSyncProvider) {
        super(view, model, controllerMgr, aSyncProvider);
    }

    public Command getSendCommand() {
        return () -> asyncCall(
                (() -> {
                        checkPassword(view.getNewPasswordField(),
                                        view.getConfirmPasswordField());
                        //if token is invalid the server will throw an exception
                        model.sendNewPasswordAndToken(  view.getTokenField(),
                                                        view.getNewPasswordField());
                }),
                (() -> controllerMgr.showLogin()));
    }

    private void checkPassword(String newPass, String confirmPass) {
        if (!newPass.equals(confirmPass)) throw new UserInputExeption("The password do not match");
        if (newPass == "") throw new UserInputExeption("The password can't be empty");
    }

    public Command getGoBackCommand() {
        return () -> controllerMgr.showEnterMail();
    }
}
