package io.rainrobot.wake.controller;

import io.rainrobot.wake.app.Controller;
import io.rainrobot.wake.app.ControllerMgr;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.model.EnterEmailModel;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.EnterEmailView;

public class EnterEmailController extends Controller<EnterEmailView, EnterEmailModel> {

    public EnterEmailController(EnterEmailView view,
                                EnterEmailModel model,
                                ControllerMgr controllerMgr,
                                ASyncProvider aSyncProvider) {
        super(view, model, controllerMgr, aSyncProvider);
    }

    public Command getSendCommand() {
        return () -> asyncCall(
                    (() -> model.sendEmail(view.getEmailField())),
                    (() -> controllerMgr.showEnterNewPassword())
        );
    }

    public Command getGoBackCommand() {
        return () -> controllerMgr.showLogin();
    }
}