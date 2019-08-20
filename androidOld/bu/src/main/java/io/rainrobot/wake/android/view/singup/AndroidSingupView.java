package io.rainrobot.wake.android.view.singup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.rainrobot.wake.android.activities.GoBackActivity;
import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.android.activities.SingupActivity;
import io.rainrobot.wake.android.configuration.AndroidView;
import io.rainrobot.wake.android.configuration.ContextMgr;
import io.rainrobot.wake.controller.SingupController;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.SingupView;

public class AndroidSingupView extends AndroidView<SingupActivity, SingupController> implements SingupView {

    public AndroidSingupView(ContextMgr contextMgr) {
        super(contextMgr, SingupActivity.class);
    }

    @Override
    public String getUsernameField() {
        return ((EditText)activity.findViewById(R.id.singUsername))
                .getText()
                .toString();
    }

    @Override
    public String getPasswordField() {
        return ((EditText)activity.findViewById(R.id.singPassword))
                .getText()
                .toString();
    }

    @Override
    public String getPasswordConfiermField() {
        return ((EditText)activity.findViewById(R.id.singConfirm))
                .getText()
                .toString();
    }

    @Override
    protected void initializeActivity() {
        setGoBackCommand(controller.getAlradyRegisterdCommand());
        setSingCommand(controller.getSingCommand());
    }

    @Override
    public void setMsg(String string) {
        ((TextView)activity.findViewById(R.id.singMsg))
                .setText(string);
    }

    @Override
    public void setSingCommand(final Command singCommand) {
        ((Button)activity.findViewById(R.id.singButton))
                .setOnClickListener((V) -> singCommand.execute());
    }

    @Override
    public void setGoBackCommand(Command goBackCommand) {
        ((GoBackActivity)activity).setGoBackCommand(goBackCommand);
    }


}
