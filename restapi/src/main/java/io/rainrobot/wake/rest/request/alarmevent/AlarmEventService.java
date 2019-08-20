package io.rainrobot.wake.rest.request.alarmevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import io.rainrobot.wake.core.Account;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.core.Sound;
import io.rainrobot.wake.rest.request.account.AccountDoa;
import io.rainrobot.wake.rest.request.device.DeviceDoa;
import io.rainrobot.wake.rest.request.preset.PresetDoa;

@Service
public class AlarmEventService {

	@Autowired
	private PresetDoa presetDoa;
	@Autowired
	private AlarmEventDoa alarmEventDoa;
	@Autowired
	private AccountDoa accountDoa;
    @Autowired
	private DeviceDoa deviceDoa;

    public AlarmEventService() {}

	public AlarmEventService(AccountDoa accountDoa,
							 PresetDoa presetDoa,
							 AlarmEventDoa alarmEventDoa,
							 DeviceDoa deviceDoa) {
		this.accountDoa = accountDoa;
		this.presetDoa = presetDoa;
		this.alarmEventDoa = alarmEventDoa;
        this.deviceDoa = deviceDoa;
    }


	public void updateAlarmEvent(String username, AlarmEvent unSavedEvent) {
		if(unSavedEvent.getDevice().getId() == null) {
			throw new IllegalStateException(
					"event must have a device with an Id");
		}
		if(unSavedEvent.getPreset().getId() == null) {
			throw new IllegalStateException(
					"event must have a preset with an Id");
		}
    	alarmEventDoa.save(unSavedEvent);
		updateLastChange(username);
	}

	private void updateLastChange(String usrNm) {
		Account account = accountDoa.getByUsername(usrNm);
		account.setLastChange(new Date());
		accountDoa.saveAndFlush(account);
	}

	public void deleteAlarmEvent(String usrNm, int id) {
		alarmEventDoa.deleteById(id);
		updateLastChange(usrNm);
	}

	public AlarmEvent createAlarmEvent(String username, Integer parentId) {
        Device device = accountDoa.findByUsername(username)
									.getDeviceList()
									.get(0);
        Preset preset = presetDoa.findById(parentId).get();
        AlarmEvent event = createEvent(preset, device);
        AlarmEvent saveEvent = alarmEventDoa.save(event);
		updateLastChange(username);
        return saveEvent;
	}

	private AlarmEvent createEvent(Preset preset, Device device) {
		AlarmEvent event = new AlarmEvent.Builder()
				.setId(null)
				.setPreset(null)
				.setDevice(null)
				.setDelay(AlarmEvent.DELAY_MIN)
				.setSound(Sound.values()[0])
				.setVol(AlarmEvent.VOL_MAX)
				.setSnooze(AlarmEvent.SNOOZE_OFF)
				.setShutOff(AlarmEvent.SHUTOFF_OFF)
				.build();
		//set methods needed for correct persistence
		event.setPreset(preset);
		event.setDevice(device);
		return event;
	}

	public List<AlarmEvent> getAllAlarmEvents(int intValue) {
		Preset preset = presetDoa.findById(intValue).get();
		return alarmEventDoa.findAllByPreset(preset);
	}
}
