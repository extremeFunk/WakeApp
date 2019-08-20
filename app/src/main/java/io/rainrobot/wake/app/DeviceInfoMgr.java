package io.rainrobot.wake.app;

import java.util.Date;

import io.rainrobot.wake.core.AlarmEvent;

public class DeviceInfoMgr {

	private IDeviceDoa deviceInfoDoa;
	private IEventDoa alarmEventDoa;
	private IRememberMeDoa rememberMeDoa;
	private ITokenDoa tokenDoa;
	private String userName;

	public DeviceInfoMgr(IDeviceDoa deviceInfoDoa,
						 IEventDoa alarmEventDoa,
						 IRememberMeDoa rememberMeDoa, ITokenDoa tokenDoa) {
		 this.deviceInfoDoa = deviceInfoDoa;
		this.alarmEventDoa = alarmEventDoa;
		this.rememberMeDoa = rememberMeDoa;
		this.tokenDoa = tokenDoa;
	}

	public void setDeviceName(String deviceName) {
		deviceInfoDoa.setDeviceName(userName, deviceName);
	}

	
	public String getDeviceName() {
		return deviceInfoDoa.getDeviceName(userName);
	}
	

	public boolean isDeviceRegister() {
		String deviceName = getDeviceName();
		if (deviceName == null || deviceName == "") {
			return false;
		}
		else return true;
	}


	public Date getEventLastChange() {
		if(userName == null) userName = getUserName();
		return deviceInfoDoa.getLastChange(userName);
	}

	public void setEvents(AlarmEvent[] events) {
		alarmEventDoa.set(events);
	}

	public void clearEvents() {
		alarmEventDoa.clear();
	}

	public void setLastEventsChange(Date lastChange) {
		if(userName == null) userName = getUserName();
		deviceInfoDoa.setLastChange(lastChange, userName);
	}

	public AlarmEvent[] getEvents() {
		return alarmEventDoa.get();
	}

	public void setUserName(String userName) {
		this.userName = userName;
		deviceInfoDoa.setUsername(userName);
	}

	public String getUserName() { return deviceInfoDoa.getUsername(); }

	public boolean isSchedulerOn() {
		return deviceInfoDoa.isSchedulerOn(userName);
	}

	public void setSchedulerOn(boolean flag) {
		deviceInfoDoa.setSchedulerOn(flag, userName);
	}

	public String getToken() {
		return tokenDoa.get();
	}

	public boolean isRememberMe() {
		return rememberMeDoa.isOn();
	}

	public void setRememberMe(boolean flag) {
		rememberMeDoa.set(flag);
	}

}
