package io.rainrobot.wake.android.view;

import android.widget.Button;
import android.widget.EditText;
import io.rainrobot.wake.android.activities.EnterEmailActivity;
import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.android.configuration.AndroidView;
import io.rainrobot.wake.android.configuration.ContextMgr;
import io.rainrobot.wake.controller.EnterEmailController;
import io.rainrobot.wake.view.EnterEmailView;

public class AndroidEnterEmailView extends AndroidView<EnterEmailActivity, EnterEmailController> implements EnterEmailView {
    public AndroidEnterEmailView(ContextMgr contextMgr) {
        super(contextMgr, EnterEmailActivity.class);
    }

    @Override
    protected void initializeActivity() {
        ((Button)findViewById(R.id.enterEmailGoBackBtn))
                .setOnClickListener((V) -> controller.getGoBackCommand());
        ((Button)findViewById(R.id.enterEmailSendBtn))
                .setOnClickListener((V) -> controller.getSendCommand());
    }

    @Override
    public String getEmailField() {
        return ((EditText)findViewById(R.id.enterEmailEditTxt)).getText().toString();
    }
}
