package io.rainrobot.wake.client;

import io.rainrobot.wake.core.Preset;

import java.util.Date;

public class PresetClient {

    private IHttpRequestSender sender;
    public final String URL;

    public PresetClient(IHttpRequestSender sender, String url) {
        this.sender = sender;
        URL = url;
    }

    public Preset[] getAllPreset() {
        return sender.get(URL, Preset[].class);
    }

    public Preset createPreset() {
        return sender.send(URL, HttpMethodEnum.POST, Preset.class, null);
    }

    public void deletePreset(Preset preset) {
        String id = String.valueOf(preset.getId());
        sender.sendWithParam(URL, id, HttpMethodEnum.DELETE, Void.class, null);
    }

    public void updatePreset(Preset preset) {
        sender.send(URL, HttpMethodEnum.PUT, Void.class, preset);
    }

    public Preset getById(int preset_id) {
        Preset preset = sender.getWithParam(URL, String.valueOf(preset_id), Preset.class);
        return preset;
    }

}
