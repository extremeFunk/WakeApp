package io.rainrobot.wake.fx.doa;

import io.rainrobot.wake.app.IRememberMeDoa;

import javax.swing.text.html.HTML;
import java.util.prefs.Preferences;

public class FxPrefRMDoa implements IRememberMeDoa {

    private static final String TAG = "rm";
    private Preferences pref;

    public FxPrefRMDoa() {
        pref = Preferences.userNodeForPackage(FxPrefRMDoa.class);
    }

    @Override
    public boolean isOn() {
        return pref.getBoolean(TAG, false);
    }

    @Override
    public void set(boolean flag) {
        pref.putBoolean(TAG, flag);
    }
}
