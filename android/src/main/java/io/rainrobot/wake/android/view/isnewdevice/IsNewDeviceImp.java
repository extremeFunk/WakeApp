package io.rainrobot.wake.android.view.isnewdevice;

import android.widget.Button;

import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.android.activities.IsNewDeviceActivity;
import io.rainrobot.wake.android.configuration.AndroidView;
import io.rainrobot.wake.android.configuration.ContextMgr;
import io.rainrobot.wake.controller.IsNewDeviceController;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.IsNewDeviceView;

public class IsNewDeviceImp extends AndroidView<IsNewDeviceActivity, IsNewDeviceController>
                            implements IsNewDeviceView {

    public IsNewDeviceImp(ContextMgr contextMgr) {
        super(contextMgr, IsNewDeviceActivity.class);
    }

    @Override
    public void setDeviceNotExistCommand(Command command) {
        ((Button)findViewById(R.id.IsNewDeviceBtnYes))
            .setOnClickListener((v) -> command.execute());
    }

    @Override
    public void setDeviceExistCommand(Command command) {
        ((Button)findViewById(R.id.IsNewDeviceBtnNO))
                .setOnClickListener((v) -> command.execute());
    }

    @Override
    protected void initializeActivity() {
        setDeviceExistCommand(controller.getDeviceExistCommand());
        setDeviceNotExistCommand(controller.getDeviceNotExistCommand());
    }
}
