package io.rainrobot.wake.app;

import java.util.Date;

public interface IDeviceDoa {
    String getUsername();
    void setUsername(String username);
    void setDeviceName(String userNm, String deviceName);
    String getDeviceName(String userNm);
    Date getLastChange(String userNm);
    void setLastChange(Date calendar, String UserMn);
    boolean isSchedulerOn(String userNm);
    void setSchedulerOn(boolean flag, String userNm);
}
