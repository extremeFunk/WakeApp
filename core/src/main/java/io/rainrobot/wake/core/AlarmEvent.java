package io.rainrobot.wake.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.rainrobot.wake.core.json.DeviceDeSerializer;
import io.rainrobot.wake.core.json.IdabelSerilizer;
import io.rainrobot.wake.core.json.PresetDeSerializer;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class AlarmEvent implements Cloneable, Idabel {
	public static final String DELAY_SUFFIX = " min.";
	public static final String SNOOZE_SUFFIX = " min.";
	public static final String SHUTOFF_SUFFIX = " min.";
	public static final String VOL_PREFIX = "%";
	public static final int DELAY_MIN = 0;
	public static final int DELAY_MAX = 60;
	public static final int SNOOZE_MIN = 1;
	public static final int SNOOZE_MAX = 30;
	public static final int SNOOZE_OFF = -1;
	public static final int SHUTOFF_OFF = -1;
	public static final int SHUTOFF_MIN = 5;
	public static final int SHUTOFF_MAX = 90;
	public static final int SHUTOFF_INTERVAL = 5;
	public static final int VOL_MIN = 1;
	public static final int VOL_MAX = 100;

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@JsonSerialize(using = IdabelSerilizer.class)
	@JsonDeserialize(using = DeviceDeSerializer.class)
	@ManyToOne(	fetch = FetchType.LAZY,
				cascade = {	CascadeType.DETACH,
							CascadeType.REFRESH })
	private Device device;

	@JsonSerialize(using = IdabelSerilizer.class)
	@JsonDeserialize(using = PresetDeSerializer.class)
	@ManyToOne(fetch = FetchType.LAZY,
				cascade = {	CascadeType.DETACH,
							CascadeType.REFRESH })
	private Preset preset;

	private int delay;
	private Sound sound;
	private int vol;
	private int snooze;
	private int shutOff;

	public AlarmEvent() {}

	public AlarmEvent(int id, Preset preset) {
		this.id = id;
		this.preset = preset;
	}

	public AlarmEvent(Integer id, Preset preset, Device device,
					  int delay, Sound sound, int vol, int snooze,
					  int shutOff) {
		this.id = id;
		this.preset = preset;
		this.device = device;
		this.delay = delay;
		this.sound = sound;
		this.vol = vol;
		this.snooze = snooze;
		this.shutOff = shutOff;
	}

	public AlarmEvent(Preset preset) {
		this.preset = preset;
	}

	public AlarmEvent(Builder b) {
		id = b.id;
		device = b.device;
		preset = b.preset;
		delay = b.delay;
		sound = b.sound;
		vol = b.vol;
		snooze = b.snooze;
		shutOff = b.shutOff;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	public Sound getSound() {
		return sound;
	}
	public void setSound(Sound sound) {
		this.sound = sound;
	}
	public int getVol() {
		return vol;
	}
	public void setVol(int vol) {
		this.vol = vol;
	}
	public int getSnooze() {
		return snooze;
	}
	public void setSnooze(int snooze) {
		this.snooze = snooze;
	}
	public int getShutOff() {
		return shutOff;
	}
	public void setShutOff(int shutOff) {
		this.shutOff = shutOff;
	}

	public Preset getPreset() {
		return preset;
	}

	public void setPreset(Preset preset) {
        if (this.preset != null) { this.preset.internalRemoveDevice(this); }
        this.preset = preset;
        if (preset != null) { preset.internalAddAlarmEvent(this); }
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
        if (this.device != null) { this.device.internalRemoveDevice(this); }
        this.device = device;
        if (device != null) { device.internalAddAlarmEvent(this); }
	}

	@JsonIgnore
	public Date getTime() {
		Date presetTime = preset.getTime();
		if (presetTime == null) {
			return null;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(presetTime);
			cal.add(Calendar.MINUTE, delay);
			return cal.getTime();
		}
	}

	public AlarmEvent clone() {
		return new Builder()
				.setDelay(delay)
				.setDevice(device)
				.setId(id)
				.setPreset(preset)
				.setShutOff(shutOff)
				.setSound(sound)
				.setVol(vol)
				.setSnooze(snooze)
				.build();
	}

	@Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "AlarmEvent{" +
				"device="
				+ ((device == null) ? " null" : device.getId()) +
				", id=" + id +
				", preset="
				+ ((preset == null) ? " null" : preset.getId()) +
				", delay=" + delay +
				", sound=" + sound +
				", vol=" + vol +
				", snooze=" + snooze +
				", shutOff=" + shutOff +
				'}';
	}

    public static class Builder {

		private Integer id;
		private Device device;
		private Preset preset;
		private int delay;
		private Sound sound;
		private int vol;
		private int snooze;
		private int shutOff;

		public AlarmEvent build() {
			return new AlarmEvent(this);
		}

		public Builder setDevice(Device device) {
			this.device = device;
			return this;
		}

		public Builder setId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder setPreset(Preset preset) {
			this.preset = preset;
			return this;
		}

		public Builder setDelay(int delay) {
			this.delay = delay;
			return this;
		}

		public Builder setSound(Sound sound) {
			this.sound = sound;
			return this;
		}

		public Builder setVol(int vol) {
			this.vol = vol;
			return this;
		}

		public Builder setSnooze(int snooze) {
			this.snooze = snooze;
			return this;
		}

		public Builder setShutOff(int shutOff) {
			this.shutOff = shutOff;
			return this;
		}
	}
}

