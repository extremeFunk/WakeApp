package io.rainrobot.wake.core;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.rainrobot.wake.core.json.DeviceDeSerializer;
import io.rainrobot.wake.core.json.IdabelSerilizer;
import io.rainrobot.wake.core.json.PresetDeSerializer;

@Entity
@JsonAutoDetect
public class Account implements Idabel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "USERNAME", nullable = false)
	private String username;
	@Column(name = "LAST_CHANGE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private Date lastChange;
	@JsonSerialize(contentUsing = IdabelSerilizer.class)
	@JsonDeserialize(contentUsing = DeviceDeSerializer.class)
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,
				orphanRemoval = true)
	private List<Device> deviceList = new ArrayList<>();
	@JsonSerialize(contentUsing = IdabelSerilizer.class)
	@JsonDeserialize(contentUsing = PresetDeSerializer.class)
	@OneToMany(cascade=CascadeType.REMOVE, fetch = FetchType.LAZY,
				orphanRemoval = true, mappedBy = "account")
	private List<Preset> presetList = new ArrayList<>();

    public Account(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public Account() {}

    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Device> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}
	public List<Preset> getPresetList() {
		if (presetList == null) {
			presetList  = new ArrayList<>();
		}
		return presetList;
	}
	public void setPresetList(List<Preset> presetList) {
		this.presetList = presetList;
	}
	public void setLastChange(Date lastChange) {
		this.lastChange = lastChange;
	}
	public Date getLastChange() {
		return lastChange;
	}


	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Account)) return false;
		else {
			Account cpr = (Account)o;

			boolean a = id == cpr.id;
			boolean b = compareStrings(username, cpr.getUsername());
			boolean c = presetList == cpr.presetList;
			boolean d = deviceList == cpr.deviceList;
			boolean e = lastChange == lastChange;

			if(a && b && c && d && e) return true;
			else return false;
		}
	}

    private boolean compareStrings(String s1, String s2) {
        if (s1 == null && s2 == null) return true;
        else if (s1 == null) return false;
        else return s1.equals(s2);
    }
}
