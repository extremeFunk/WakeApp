package io.rainrobot.wake.android.view;

import io.rainrobot.wake.android.activities.EnterNewPasswordActivity;
import io.rainrobot.wake.android.configuration.AndroidView;
import io.rainrobot.wake.android.configuration.ContextMgr;
import io.rainrobot.wake.controller.EnterNewPasswordController;
import io.rainrobot.wake.view.EnterNewPasswordView;

public class AndroidEnterNewPasswordView extends AndroidView<EnterNewPasswordActivity, EnterNewPasswordController> implements EnterNewPasswordView {
    public AndroidEnterNewPasswordView(ContextMgr contextMgr) {
        super(contextMgr, EnterNewPasswordActivity.class);
    }

    @Override
    protected void initializeActivity() {

    }

    @Override
    public String getTokenField() {
        return null;
    }

    @Override
    public String getNewPasswordField() {
        return null;
    }

    @Override
    public String getConfirmPasswordField() {
        return null;
    }
}
