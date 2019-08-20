package io.rainrobot.wake.android.activities;
@Deprecated
public class ApplicationMgrHolder {

    private static ApplicationMgr applicationMgr;

    public static void setMgr(ApplicationMgr mgr) {
        applicationMgr = mgr;
    }

    public static ApplicationMgr getMgr() {
        return  applicationMgr;
    }
}
