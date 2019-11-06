package io.rainrobot.wake.rest.dto;

public class Path {
    public static final String AUTH_HEADER_LABEL = "Authorization";

    public static final String GLOBAL_URL_PREFIX = "https://wake-app-rr.herokuapp.com";

    public static final String ACCOUNT = "/account";
    public static final String USERNAME = "/username";
    public static final String LASTCHANGE = "/lastchange";
    public static final String TESTTOKEN = "/testtoken";
    public static final String SINGUP = "/singup";
    public static final String PRESET =  "/preset";
    public static final String DEVICE =  "/device";
    public static final String DEVICE_EVENTS = DEVICE + "/events";
    // DO NOT DELETE (event(s!))
    public static final String ALARMEVENT = "/alarmevent";
    public static final String ID = "/{id}";
    public static final String RESETPASSWORD = "/reset_password";
    private static final String LOGIN = "/login";

    public static String getAccoutUrl() {
        return GLOBAL_URL_PREFIX + ACCOUNT;
    }
    public static String getAlarmUrl() { return GLOBAL_URL_PREFIX + ALARMEVENT; }
    public static String getDeviceUrl() { return GLOBAL_URL_PREFIX + DEVICE; }
    public static String getLoginUrl() { return GLOBAL_URL_PREFIX + LOGIN; }
    public static String getPresetUrl() { return GLOBAL_URL_PREFIX + PRESET; }
    public static String getSighupUrl() { return GLOBAL_URL_PREFIX + SINGUP; }
    public static String tokenTestUrl() { return GLOBAL_URL_PREFIX + TESTTOKEN; }
    public static String getLastChangeUrl() { return GLOBAL_URL_PREFIX + LASTCHANGE; }
    public static String getUsernameUrl() { return GLOBAL_URL_PREFIX + USERNAME; }
}
