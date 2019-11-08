package io.rainrobot.wake.rest.dto;

import lombok.Builder;

import java.util.List;
@Builder
public class Device {
	private String name;
	private List<AlarmEvent> eventList;
}
