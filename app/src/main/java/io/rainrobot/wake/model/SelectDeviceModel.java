package io.rainrobot.wake.model;

import java.util.Arrays;
import java.util.List;

import io.rainrobot.wake.app.IModel;
import io.rainrobot.wake.app.DeviceInfoMgr;
import io.rainrobot.wake.client.AccountClient;
import io.rainrobot.wake.client.DeviceClient;
import io.rainrobot.wake.core.Device;

public class SelectDeviceModel implements IModel {
	
	private DeviceInfoMgr deviceInfoMgr;
	private DeviceClient deviceClient;
	private AccountClient accountClient;

	public SelectDeviceModel(DeviceInfoMgr deviceInfoMgr, DeviceClient deviceClient, AccountClient accountClient) {
		this.deviceInfoMgr = deviceInfoMgr;
		this.deviceClient = deviceClient;
		this.accountClient = accountClient;
	}

	public List<Device> getDevices() {
		return Arrays.asList(deviceClient.getAllDevices());
	}

	public void setThisDevice(Device device) {
		deviceInfoMgr.setDeviceName(device.getName());
	}
}
