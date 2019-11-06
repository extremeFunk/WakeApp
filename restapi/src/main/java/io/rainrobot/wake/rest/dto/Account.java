package io.rainrobot.wake.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class Account {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private Date lastChange;
	private List<Device> deviceList;
	private List<Preset> presetList;
}
