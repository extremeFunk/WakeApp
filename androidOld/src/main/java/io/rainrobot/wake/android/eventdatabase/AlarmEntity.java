package io.rainrobot.wake.android.eventdatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.util.Calendar;
import java.util.Date;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.core.Sound;

@Entity
public class AlarmEntity {

    public AlarmEntity() {
        
    }

    @PrimaryKey
    private int id;
    private int delay;
    private String name;
    private int snooze;
    @TypeConverters(SoundConverter.class)
    private Sound sound;
    private int stop;
    @TypeConverters(DateConverter.class)
    private Date time;
    private int vol;


    public AlarmEntity(AlarmEvent alarm) {
        this.id = alarm.getId();
        this.delay = alarm.getDelay();
        this.name = alarm.getPreset().getName();
        this.snooze = alarm.getSnooze();
        this.sound = alarm.getSound();
        this.stop = alarm.getShutOff();
        this.time = alarm.getPreset().getTime();
        this.vol = alarm.getVol();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSnooze() {
        return snooze;
    }

    public void setSnooze(int snooze) {
        this.snooze = snooze;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getVol() {
        return vol;
    }

    public void setVol(int vol) {
        this.vol = vol;
    }

    public AlarmEvent toObj() {
        Preset preset = new Preset();
        preset.setName(name);
        preset.setTime(time);
        AlarmEvent obj = new AlarmEvent(
                                id,
                                preset,
                                null,
                                delay,
                                sound,
                                vol,
                                snooze,
                                stop);
        return obj;
    }
}

class DateConverter {

    @TypeConverter
    public static Date toDate(Long dateLong){
        if(dateLong == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateLong);
        return calendar.getTime();
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}

class SoundConverter {

    @TypeConverter
    public static Sound toSound(String name) {
        return name == null ? null : Sound.valueOf(name);
    }
    @TypeConverter
    public static String fromSound(Sound sound) {
        return sound == null ? null : sound.name();
    }
}
