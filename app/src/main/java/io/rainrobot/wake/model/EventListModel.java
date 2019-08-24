package io.rainrobot.wake.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import io.rainrobot.wake.app.EventUpdateMgr;
import io.rainrobot.wake.app.IModel;
import io.rainrobot.wake.client.AlarmEventClient;
import io.rainrobot.wake.client.DeviceClient;
import io.rainrobot.wake.client.PresetClient;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;

public class EventListModel implements IModel {

	private AlarmEventClient eventClient;
	private PresetClient presetClient;
	private DeviceClient deviceClient;
	private EventUpdateMgr eventUpdateMgr;


	public EventListModel(AlarmEventClient eventClient,
						  PresetClient presetClient,
						  DeviceClient deviceClient,
						  EventUpdateMgr eventUpdateMgr) {
		this.eventClient = eventClient;
		this.presetClient = presetClient;
		this.deviceClient = deviceClient;
		this.eventUpdateMgr = eventUpdateMgr;
	}

	public List<AlarmEvent> getAllEvents(int preset_id) {
		return new ArrayList(Arrays.asList(eventClient.getAllEvents(preset_id)));
	}

	public AlarmEvent createEvent(int preset_id) {
		AlarmEvent event = eventClient.createAlarmEvent(preset_id);
		eventUpdateMgr.updateEvents();
		return event;
	}

	public List<Device> getAllDevices(int presetId) {
		return Arrays.asList(deviceClient.getAllDevices());
	}

	public void removeEvent(int event_id) {
		eventClient.deleteAlarmEvent(event_id);
//		removeFromList(event_id);
		eventUpdateMgr.updateEvents();
	}

//	protected void removeFromList(int event_id) {
//		Optional<AlarmEvent> optional = searchListById(event_id);
//		if(optional.isPresent()) alarmList.remove(optional.get());
//	}
//
//	protected Optional<AlarmEvent> searchListById(int event_id) {
//		return alarmList
//				.stream()
//				.filter(prst -> (prst.getId().equals(event_id))).findFirst();
//	}

	public void updateEvent(AlarmEvent event) {
		eventClient.updateAlarmEvent(event);
		eventUpdateMgr.updateEvents();
	}
}
