package io.rainrobot.wake.android.view;

import io.rainrobot.wake.android.activities.EnterEmailActivity;
import io.rainrobot.wake.android.configuration.AndroidView;
import io.rainrobot.wake.android.configuration.ContextMgr;
import io.rainrobot.wake.controller.EnterEmailController;
import io.rainrobot.wake.view.EnterEmailView;

public class enterEmailView extends AndroidView<EnterEmailActivity, EnterEmailController> implements EnterEmailView {
    public enterEmailView(ContextMgr contextMgr) {
        super(contextMgr, EnterEmailActivity.class);
    }

    @Override
    protected void initializeActivity() {

    }

    @Override
    public String getEmailField() {
        return null;
    }

    @Override
    public void setController(EnterEmailController controller) {

    }
}
