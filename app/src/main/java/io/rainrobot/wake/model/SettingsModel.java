package io.rainrobot.wake.model;

import io.rainrobot.wake.app.IModel;
import io.rainrobot.wake.app.DeviceInfoMgr;
import io.rainrobot.wake.client.AccountClient;
import io.rainrobot.wake.client.DeviceClient;
import io.rainrobot.wake.core.Device;

public class SettingsModel implements IModel {

	private AccountClient accountClient;
	private DeviceClient deviceClient;
	private DeviceInfoMgr deviceInfoMgr;

	public SettingsModel(AccountClient accountClient,
						 DeviceClient deviceClient,
						 DeviceInfoMgr deviceInfoMgr) {
		this.accountClient = accountClient;
		this.deviceClient = deviceClient;
		this.deviceInfoMgr = deviceInfoMgr;
	}

	public void registerDevice(String deviceName) throws DeviceNameTakenException {
		checkIfNameTaken(deviceName);
		Device device = getDevice();
		device.setName(deviceName);
		deviceClient.updateDevice(device);
		deviceInfoMgr.setDeviceName(deviceName);
	}

	private Device getDevice() {
		String name = deviceInfoMgr.getDeviceName();
		
		for (Device d : deviceClient.getAllDevices()) {
			if(d.getName() == name) {
				return d;
			}
		}
		
		throw new NullPointerException("No device was found with name " + name);
	}

	private void checkIfNameTaken(String name) throws DeviceNameTakenException {
		for(Device dev : deviceClient.getAllDevices()) {
			if(dev.getName() == name) {
				throw new DeviceNameTakenException();
			}
		}
	}

	public String getDeviceName() {
		return deviceInfoMgr.getDeviceName();
	}
	

}
