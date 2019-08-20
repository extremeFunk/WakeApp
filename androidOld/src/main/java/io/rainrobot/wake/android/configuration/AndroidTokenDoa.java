package io.rainrobot.wake.android.configuration;

import android.content.SharedPreferences;

import io.rainrobot.wake.app.ITokenDoa;

public class AndroidTokenDoa implements ITokenDoa {
    private SharedPreferences pref;
    private final String TAG = "Token";

    public AndroidTokenDoa(SharedPreferences pref) {
        this.pref = pref;
    }

    @Override
    public void save(String token) {
        pref.edit().putString(TAG, token).commit();
    }

    @Override
    public String get() {
        return pref.getString(TAG, "");
    }
}
