package io.rainrobot.wake.client;


import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.Path;


public class DeviceClient {

	private IHttpRequestSender sender;
	public final String URL;


	public DeviceClient(IHttpRequestSender sender, String URL) {
		this.sender = sender;
		this.URL = URL;
	}

	public Device createDevice() {
		return sender.send(URL, HttpMethodEnum.POST, Device.class, null);
	}
	
	public void updateDevice(Device device) {
		sender.send(URL, HttpMethodEnum.PUT, Void.class, device);
	}
	
	public void deleteDevice(Device device) {
		Integer id = device.getId();
		sender.sendWithParam(URL, id.toString(), HttpMethodEnum.DELETE, Void.class, null);
	}

	public Device getById(Integer deviceId) {
		return sender.getWithParam(URL, deviceId.toString(), Device.class);
	}

	public Device[] getAllDevices() {
		return sender.get(URL, Device[].class);
	}

	public AlarmEvent[] getEventsByName(String deviceName) {
		return sender.sendWithParam(URL + Path.ALARMEVENT,
									deviceName,
									HttpMethodEnum.GET,
									AlarmEvent[].class,
									null);
	}
}
