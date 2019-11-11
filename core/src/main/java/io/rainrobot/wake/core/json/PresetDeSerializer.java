package io.rainrobot.wake.core.json;

import io.rainrobot.wake.core.Preset;

public class PresetDeSerializer extends IdabelDeSerializer<Preset>{
    @Override
    protected Preset getInstance() {
        return new Preset();
    }
}
