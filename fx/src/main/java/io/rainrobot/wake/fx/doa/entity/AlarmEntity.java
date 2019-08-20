package io.rainrobot.wake.fx.doa.entity;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.core.Sound;
import io.rainrobot.wake.core.util.DateUtil;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AlarmEntity {
    @Id
    private Integer id;
    private String sound;
    private long presetTime;
    private Integer presetId;
    private String presetName;
    private int snooze;
    private int shutoff;
    private int vol;
    private int delay;

    public AlarmEntity() {}
    public AlarmEntity(AlarmEvent event) {
        this.id = event.getId();
        this.sound = event.getSound().name();
        this.presetTime = event.getTime().getTime();
        this.presetId = event.getPreset().getId();
        this.presetName = event.getPreset().getName();
        this.snooze = event.getSnooze();
        this.shutoff = event.getShutOff();
        this.vol = event.getVol();
        this.delay = event.getDelay();
    }

    public AlarmEvent getEvent() {
        return new AlarmEvent.Builder()
                .setId(id)
                .setDelay(delay)
                .setShutOff(shutoff)
                .setSnooze(snooze)
                .setSound(Sound.valueOf(sound))
                .setVol(vol)
                .setPreset(new Preset(presetId, presetName, DateUtil.fromMili(presetTime)))
                .setDevice(null)
                .build();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public long getPresetTime() {
        return presetTime;
    }

    public void setPresetTime(long presetTime) {
        this.presetTime = presetTime;
    }

    public Integer getPresetId() {
        return presetId;
    }

    public void setPresetId(Integer presetId) {
        this.presetId = presetId;
    }

    public int getSnooze() {
        return snooze;
    }

    public void setSnooze(int snooze) {
        this.snooze = snooze;
    }

    public int getShutoff() {
        return shutoff;
    }

    public void setShutoff(int shutoff) {
        this.shutoff = shutoff;
    }

    public int getVol() {
        return vol;
    }

    public void setVol(int vol) {
        this.vol = vol;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }


}
