package io.rainrobot.wake.android.activities;

import io.rainrobot.wake.android.configuration.WakeActivity;
import io.rainrobot.wake.util.Command;

public abstract class GoBackActivity extends WakeActivity {

    private Command goBackCommand;

    public void setGoBackCommand(Command goBackCommand) {
        this.goBackCommand = goBackCommand;
    }

    @Override
    public void onBackPressed() {
        if(goBackCommand != null) {
            goBackCommand.execute();
        }
    }
}
