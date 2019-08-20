package io.rainrobot.wake.fx;

import io.rainrobot.wake.fx.config.FxConfiguration;

public class ConfigContainer {
    private static FxConfiguration config;

    public static FxConfiguration getConfig() {
        return config;
    }

    public static void setConfig(FxConfiguration config) {
        ConfigContainer.config = config;
    }
}
