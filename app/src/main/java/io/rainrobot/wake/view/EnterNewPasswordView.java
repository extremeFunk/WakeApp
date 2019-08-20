package io.rainrobot.wake.view;

import io.rainrobot.wake.app.IView;
import io.rainrobot.wake.controller.EnterNewPasswordController;

public interface EnterNewPasswordView extends IView<EnterNewPasswordController> {
    String getTokenField();

    String getNewPasswordField();

    String getConfirmPasswordField();
}
