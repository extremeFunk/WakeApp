package io.rainrobot.wake.rest.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.List;
@Builder
@EqualsAndHashCode
public class Device {
	private String name;
	private List<AlarmEvent> eventList;
}
