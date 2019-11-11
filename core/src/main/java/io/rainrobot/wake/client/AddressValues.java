package io.rainrobot.wake.client;

public class AddressValues {

    public static final String GLOBAL_URL_PREFIX = "https://wake-app-rr.herokuapp.com";

    public static final String AUTH_HEADER_LABEL = "Authorization";

    public static String getAccoutUrl() {
        return GLOBAL_URL_PREFIX + "/account";
    }

    public static String getAlarmUrl() { return GLOBAL_URL_PREFIX + "/alarmevent"; }

    public static String getDeviceUrl() { return GLOBAL_URL_PREFIX + "/device"; }

    public static String getLoginUrl() { return GLOBAL_URL_PREFIX + "/login"; }

    public static String getPresetUrl() {
        return GLOBAL_URL_PREFIX + "/preset";
    }

    public static String getSighupUrl() {
        return GLOBAL_URL_PREFIX + "/singup";
    }

    public static String tokenTestUrl() {
        return GLOBAL_URL_PREFIX + "/testtoken";
    }

    public static String getResetPasswordUrl() { return GLOBAL_URL_PREFIX + "/reset_password"; }
}