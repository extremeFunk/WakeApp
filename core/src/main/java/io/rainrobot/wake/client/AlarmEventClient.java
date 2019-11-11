package io.rainrobot.wake.client;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Path;

public class AlarmEventClient {
	
	private IHttpRequestSender sender;
	private final String URL;

	public AlarmEventClient(IHttpRequestSender sender, String url){
		this.sender = sender;
		this.URL = url;
	}

	public AlarmEvent createAlarmEvent(Integer presetParentId) {
		return sender.sendWithParam(URL + Path.PRESET,
										presetParentId.toString(),
										HttpMethodEnum.POST,
										AlarmEvent.class,
										null);
	}
	
	public void updateAlarmEvent(AlarmEvent alarmevent) {
		sender.send(URL, HttpMethodEnum.PUT, Void.class, alarmevent);
	}
	
	public void deleteAlarmEvent(Integer id) {
		sender.sendWithParam(URL, id.toString(), HttpMethodEnum.DELETE, Void.class, null);
	}

	public AlarmEvent[] getAllEvents(Integer preset_id) {
		return sender.getWithParam(URL + Path.PRESET, preset_id.toString(), AlarmEvent[].class);
	}

}
