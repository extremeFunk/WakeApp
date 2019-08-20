package io.rainrobot.wake.android.view.settings;

import android.widget.Button;
import android.widget.EditText;

import io.rainrobot.wake.android.activities.GoBackActivity;
import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.android.activities.SettingsActivity;
import io.rainrobot.wake.android.configuration.AndroidView;
import io.rainrobot.wake.android.configuration.ContextMgr;
import io.rainrobot.wake.controller.SettingsController;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.SettingsView;


public class AndroidSettingsView extends AndroidView<SettingsActivity, SettingsController> implements SettingsView {

    public AndroidSettingsView(ContextMgr contextMgr) {
        super(contextMgr, SettingsActivity.class);
    }

    @Override
    public String getInputField() {
        return getDeviceNameTxtIn().getText().toString();
    }


    @Override
    public void setRegisterCommand(Command command) {
        ((Button)findViewById(R.id.SettingsSaveBtn))
                .setOnClickListener((V) -> command.execute());
    }

    @Override
    public void setGoBackCommand(Command command) {
        ((GoBackActivity)activity).setGoBackCommand(command);
    }

    @Override
    public void setDeviceName(String string) {
        getDeviceNameTxtIn().setText(string);
    }

    private EditText getDeviceNameTxtIn() {
        return (EditText)findViewById(R.id.SettingsDeviceNameTxtIn);
    }

    @Override
    protected void initializeActivity() {
        controller.injectDataToView();
        setRegisterCommand(controller.getDeviceNameCommand());
        setGoBackCommand(controller.getGoBackCommand());
    }
}
