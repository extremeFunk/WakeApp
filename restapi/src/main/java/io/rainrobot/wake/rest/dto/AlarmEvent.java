package io.rainrobot.wake.rest.dto;

public class AlarmEvent {
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

	private Integer id;
	private Device device;
	private int delay;
	private Sound sound;
	private int vol;
	private int snooze;
	private int shutOff;

}

