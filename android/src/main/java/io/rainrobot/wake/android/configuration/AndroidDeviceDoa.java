package io.rainrobot.wake.android.configuration;

import android.content.SharedPreferences;

import java.util.Date;

import io.rainrobot.wake.app.IDeviceDoa;

public class AndroidDeviceDoa implements IDeviceDoa {

    private SharedPreferences pref;
    private final String DEVICE_NAME = " device name";
    private final String SCHEDULER_ON = " scheduler on";
    private final String USERNAME = "username";
    private final long VALUE_NOT_FOUND = -1;
    private final String TAG = " Last Event Change";


    public AndroidDeviceDoa(SharedPreferences pref) {
        this.pref = pref;
    }

    @Override
    public String getUsername() {
        return pref.getString(USERNAME, "");
    }

    @Override
    public void setUsername(String username) {
        pref.edit().putString(USERNAME, username).commit();
    }

    @Override
    public void setDeviceName(String username, String deviceName) {
        pref.edit().putString(username + DEVICE_NAME , deviceName).commit();
    }

    @Override
    public String getDeviceName(String username) {
        return pref.getString(username + DEVICE_NAME, "");
    }



    @Override
    public Date getLastChange(String userNm) {
        long longVal = pref.getLong(userNm + TAG, VALUE_NOT_FOUND);
        if (longVal == VALUE_NOT_FOUND) {
            throw new RuntimeException("No value present for last change id db - " + userNm);
        }
        return new Date(longVal);
    }

    @Override
    public void setLastChange(Date calendar, String userNm) {
        pref.edit().putLong(userNm + TAG, calendar.getTime()).commit();
    }

    @Override
    public boolean isSchedulerOn(String userNm) {
        return pref.getBoolean(userNm + SCHEDULER_ON, false);
    }

    @Override
    public void setSchedulerOn(boolean flag, String userNm) {
        pref.edit().putBoolean(userNm + SCHEDULER_ON, flag).commit();
    }


}
