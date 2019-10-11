package io.rainrobot.wake.android.view.devicereg;

import android.widget.Button;
import android.widget.EditText;

import io.rainrobot.wake.android.activities.DeviceRegActivity;
import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.android.configuration.AndroidView;
import io.rainrobot.wake.android.configuration.ContextMgr;
import io.rainrobot.wake.controller.DeviceRegController;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.DeviceRegView;

public class AndroidDeviceRegView extends AndroidView<DeviceRegActivity, DeviceRegController>
                                implements DeviceRegView {

    public AndroidDeviceRegView(ContextMgr contextMgr) {
        super(contextMgr, DeviceRegActivity.class);
    }

    @Override
    public String getInputField() {
        return ((EditText)activity.findViewById(R.id.DeviceRegTxtIn))
                .getText()
                .toString();
    }



    @Override
    public void setRegisterCommand(Command command) {
        ((Button)activity.findViewById(R.id.DeviceRegBtn))
                .setOnClickListener((v) -> command.execute());
    }

    @Override
    protected void initializeActivity() {
        setRegisterCommand(controller.getRegisterCommand());
    }
}
