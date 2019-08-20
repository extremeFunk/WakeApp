package io.rainrobot.wake.fx.doa;

import io.rainrobot.wake.app.IDeviceDoa;
import io.rainrobot.wake.core.util.DateUtil;

import java.util.Date;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class FxDeviceDoa implements IDeviceDoa {
    private static final String USERNAME_TAG = "uNm";
    private static final String NO_VALUE_STIRNG = "";
    private static final String DEVICE_TAG = "dev ";
    private static final String LAST_CHANGE_TAG = "lstChng ";
    private static final long NO_VALUE_LONG = -1;
    private static final String SCHEDULER_ON_TAG = "schdlualOn ";
    private Preferences pref;
    public FxDeviceDoa() {
         pref = Preferences.userNodeForPackage(FxDeviceDoa.class);
    }

    public void clear() {
        try {
            pref.clear();
        } catch (BackingStoreException e) {

        }
    }

    @Override
    public String getUsername() {
        return pref.get(USERNAME_TAG, NO_VALUE_STIRNG); // "a string"
    }

    @Override
    public void setUsername(String username) {
        pref.put(USERNAME_TAG, username);
    }

    @Override
    public void setDeviceName(String userNm, String deviceName) {
        pref.put(DEVICE_TAG + userNm, deviceName );
    }

    @Override
    public String getDeviceName(String userNm) {
        return pref.get(DEVICE_TAG + userNm, NO_VALUE_STIRNG);
    }

    @Override
    public Date getLastChange(String userNm) {
        return DateUtil.fromMili(
                pref.getLong(LAST_CHANGE_TAG + userNm, NO_VALUE_LONG));
    }

    @Override
    public void setLastChange(Date time, String userNm) {
        pref.putLong(LAST_CHANGE_TAG + userNm, time.getTime());
    }

    @Override
    public boolean isSchedulerOn(String userNm) {
        //on fx app there is a need to always return false
        //old line -> return pref.getBoolean(SCHEDULER_ON_TAG + userNm, false);
        return false;
    }

    @Override
    public void setSchedulerOn(boolean flag, String userNm) {
        pref.putBoolean(SCHEDULER_ON_TAG + userNm, flag);
    }
}
