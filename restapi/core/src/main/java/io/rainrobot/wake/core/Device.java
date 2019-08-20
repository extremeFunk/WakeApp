package io.rainrobot.wake.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.rainrobot.wake.core.json.AlarmEventDeSerializer;
import io.rainrobot.wake.core.json.IdabelCollectionSerializer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Device implements Idabel{
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NAME")
	private String name;
	@JsonSerialize(using = IdabelCollectionSerializer.class)
	@JsonDeserialize(contentUsing = AlarmEventDeSerializer.class)
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "device")
	private List<AlarmEvent> eventList = new ArrayList<>();

	public Device() {}

	public Device(String deviceName) {
		this.name = deviceName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<AlarmEvent> getEventList() {
		if (eventList == null) eventList = new ArrayList<>();
		return eventList;
	}

	public void setEventList(List<AlarmEvent> eventList) {
		this.eventList = eventList;
	}

	public void addEvent(AlarmEvent event) { event.setDevice(this); }
	public void internalAddAlarmEvent(AlarmEvent event) { eventList.add(event); }

	public void removeAlarmEvent(AlarmEvent event) { event.setDevice(null); }
	public void internalRemoveDevice(AlarmEvent event) { eventList.remove(event); }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Device event = (Device) o;
		return id != null && id.equals(event.getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}

}
