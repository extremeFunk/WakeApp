package io.rainrobot.wake.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
@Builder
@EqualsAndHashCode
public class Preset {
	private String name;
	private List<AlarmEvent> alarmEventList;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SS Z")
	private Date time;
	private Boolean activeState;

}

