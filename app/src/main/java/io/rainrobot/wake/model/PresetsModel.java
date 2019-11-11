package io.rainrobot.wake.model;

import java.util.*;

import io.rainrobot.wake.client.PresetClient;
import io.rainrobot.wake.app.EventUpdateMgr;
import io.rainrobot.wake.app.IModel;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.core.util.DateUtil;

public class PresetsModel implements IModel {

	private PresetClient client;
	private EventUpdateMgr eventUpdateMgr;

	public PresetsModel(PresetClient client, EventUpdateMgr eventUpdateMgr) {
		this.client = client;
		this.eventUpdateMgr = eventUpdateMgr;
	}

	public List<Preset> getPresets()  {
		Preset[] arr = client.getAllPreset();
		List<Preset> list = new ArrayList<>();
		for(Preset p : arr) {
			list.add(p);}
		return list;
	}

	public Preset createPreset() {
		Preset preset = client.createPreset();
		eventUpdateMgr.updateEvents();
		return preset;
	}

	public void deletePreset(int id) {
		client.deletePreset(findById(id));
	}


	public void updateName(int id, String name) {
		Preset preset = findById(id);
		preset.setName(name);
		client.updatePreset(preset);
		
	}

	public void setActiveState(int id, boolean state) {
		Preset preset = findById(id);
		preset.setActiveState(state);
		client.updatePreset(preset);
		eventUpdateMgr.updateEvents();
	}

	private Preset findById(int id) {
		return getPresets().stream().filter(
								p -> 
								p	.getId() == id)
									.findFirst().get();

	}

	public void updateTime(int id, Date time) {
		Preset preset = findById(id);
		preset.setTime(time);
		client.updatePreset(preset);
		eventUpdateMgr.updateEvents();
	}
	
	
}
