package io.rainrobot.wake.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.rainrobot.wake.core.json.AccountDeSerializer;
import io.rainrobot.wake.core.json.AlarmEventDeSerializer;
import io.rainrobot.wake.core.json.IdabelCollectionSerializer;
import io.rainrobot.wake.core.json.IdabelDeSerializer;
import io.rainrobot.wake.core.json.IdabelSerilizer;

@Entity
public class Preset implements Idabel{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	private String name;

	@JsonSerialize(using = IdabelCollectionSerializer.class)
	@JsonDeserialize(contentUsing = AlarmEventDeSerializer.class)
//	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval = true, mappedBy = "preset")
	@OneToMany(fetch=FetchType.LAZY,
			cascade=CascadeType.REMOVE,
			orphanRemoval = true,
			mappedBy = "preset")
	private Set<AlarmEvent> alarmEventList = new HashSet<>();

    @ManyToOne(cascade =
            {
//			        CascadeType.PERSIST,
                    CascadeType.DETACH,
//							CascadeType.MERGE,
                    CascadeType.REFRESH}
    )
    @JsonSerialize(using = IdabelSerilizer.class)
    @JsonDeserialize(using = AccountDeSerializer.class)
    private Account account;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
	@Temporal(TemporalType.TIME)
	private Date time;

	@Column(columnDefinition = "TINYINT", length = 1)
	private Boolean activeState;
	
	public Preset() {};
	
	public Preset(int id, 
					String name, 
					Set<AlarmEvent> alarmEventList,
					boolean active) {
		this.id = id;
		this.name = name;
		this.alarmEventList = alarmEventList;
		this.activeState = Boolean.valueOf(active);
	}
	
	public Preset(Account account, String name, Date time, boolean active) {
		this.account = account;
		this.name = name;
		this.time = time;
		this.activeState = Boolean.valueOf(active);
	}


	public Preset(Integer id) {
		this.id = id;
	}

	public Preset(Integer id, String name, Date time) {
		this.id = id;
		this.name = name;
		this.time = time;
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

	public Set<AlarmEvent> getAlarmEventList() {
		if (alarmEventList == null) alarmEventList = new HashSet<>();
        return alarmEventList;
	}
	@JsonIgnore
	public Set<AlarmEvent> getDevice() {
		return Collections.unmodifiableSet(alarmEventList);
	}

	public void addEvent(AlarmEvent event) { event.setPreset(this); }
	public void internalAddAlarmEvent(AlarmEvent event) { alarmEventList.add(event); }

	public void removeAlarmEvent(AlarmEvent event) { event.setPreset(null); }
	public void internalRemoveDevice(AlarmEvent event) { alarmEventList.remove(event); }


	public boolean isActiveState() {
		return activeState.booleanValue();
	}

	public void setActiveState(Boolean activeState) {
		this.activeState = Boolean.valueOf(activeState);
	}

	public void setActiveState(boolean activeState) {
		this.activeState = activeState;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getTime() {
		return time;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Account getAccount() {
		return this.account;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Preset event = (Preset) o;
		return id != null && id.equals(event.getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}
}

