package io.rainrobot.wake.android.configuration;

import android.content.SharedPreferences;

import android.widget.Toast;
import io.rainrobot.wake.app.IRememberMeDoa;

public class AndroidRememberMeDoa implements IRememberMeDoa {

    SharedPreferences pref;
    private final String tag = "Remember Me State";

    public AndroidRememberMeDoa(SharedPreferences pref) {
        this.pref = pref;
    }

    @Override
    public boolean isOn() {
        return pref.getBoolean(tag, false);
    }

    @Override
    public void set(boolean flag) {
        pref.edit().putBoolean(tag, flag).commit();
    }
}
