package io.rainrobot.wake.android.view;

import android.widget.Button;
import android.widget.EditText;
import io.rainrobot.wake.android.activities.EnterNewPasswordActivity;
import io.rainrobot.wake.android.activities.R;
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
        ((Button)findViewById(R.id.enterNewPasswordGoBackBtn))
                .setOnClickListener((V) -> controller.getGoBackCommand());
        ((Button)findViewById(R.id.enterNewPasswordSendBtn))
                .setOnClickListener((V) -> controller.getSendCommand());
    }

    @Override
    public String getTokenField() {
        return ((EditText)findViewById(R.id.enterNewPasswordValidationCodeEditTxt))
                .getText().toString();
    }

    @Override
    public String getNewPasswordField() {
        return ((EditText)findViewById(R.id.enterNewPasswordEditTxt))
                .getText().toString();
    }

    @Override
    public String getConfirmPasswordField() {
        return ((EditText)findViewById(R.id.enterNewPasswordConfirmEditTxt))
                .getText().toString();
    }
}
