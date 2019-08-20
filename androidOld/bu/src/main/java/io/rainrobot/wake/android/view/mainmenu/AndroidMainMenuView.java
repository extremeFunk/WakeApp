package io.rainrobot.wake.android.view.mainmenu;

import android.widget.Button;

import io.rainrobot.wake.android.activities.MainMenuActivity;
import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.android.configuration.AndroidView;
import io.rainrobot.wake.android.configuration.ContextMgr;
import io.rainrobot.wake.controller.MainMenuController;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.MainMenuView;

public class AndroidMainMenuView    extends AndroidView<MainMenuActivity, MainMenuController>
                                    implements MainMenuView {

    public AndroidMainMenuView(ContextMgr contextMgr) {
        super(contextMgr, MainMenuActivity.class);
    }

    @Override
    public void setPresetsCommand(Command command) {
        ((Button)activity.findViewById(R.id.mainPresetsBtn))
                .setOnClickListener((v) -> command.execute());
    }

    @Override
    public void setSettingsCommand(Command command) {
        ((Button)activity.findViewById(R.id.mainSettingsBtn))
                .setOnClickListener((v) -> command.execute());
    }

    @Override
    public void setDeviceEventsCommand(Command command) {
        ((Button)activity.findViewById(R.id.mainDeviceEventsBtn))
                .setOnClickListener((v) -> command.execute());
    }

    @Override
    public void setLogoutCommand(Command command) {
        ((Button)activity.findViewById(R.id.mainLogoutBtn))
                .setOnClickListener((v) -> command.execute());
    }

    @Override
    protected void initializeActivity() {
        setDeviceEventsCommand(controller.getDeviceEventsCommand());
        setLogoutCommand(controller.getLogoutCommand());
        setPresetsCommand(controller.getPresetsCommand());
        setSettingsCommand(controller.getSettingsCommand());
    }
}
