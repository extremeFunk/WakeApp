package io.rainrobot.wake.view;

import io.rainrobot.wake.app.IView;
import io.rainrobot.wake.controller.EnterEmailController;

public interface EnterEmailView extends IView<EnterEmailController> {
    String getEmailField();
}
