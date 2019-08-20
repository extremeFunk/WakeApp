package io.rainrobot.wake.fx.doa;

import io.rainrobot.wake.app.ITokenDoa;

import java.util.prefs.Preferences;

public class FxPrefTokenDoa  implements ITokenDoa {
    private static final String TAG = "tok";
    private Preferences pref;

    public FxPrefTokenDoa() {
        pref = Preferences.userNodeForPackage(FxPrefTokenDoa.class);
    }

    @Override
    public String get() {
        return pref.get(TAG, "");
    }

    @Override
    public void save(String token) {
        pref.put(TAG, token);
    }
}
