package io.rainrobot.wake.rest.request.device;

import io.rainrobot.wake.core.Account;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.rest.request.account.AccountDoa;
import io.rainrobot.wake.rest.request.alarmevent.AlarmEventDoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DeviceService {

	@Autowired
	AccountDoa accountDoa;
	@Autowired
	DeviceDoa deviceDoa;
	@Autowired
	AlarmEventDoa alarmEventDoa;

	public List<Device> findAllByUserame(String username) {
		return getAccountByUsername(username).getDeviceList();
	}

	private Account getAccountByUsername(String username) {
		return accountDoa.findByUsername(username);
	}

	public void saveDevice(Device preset) {
		deviceDoa.save(preset);
	}

	public void deleteDevice(String usrNm, int id) {
		Account account = accountDoa.findByUsername(usrNm);
		boolean isDeviceInAccount = account.getDeviceList()
											.stream()
											.anyMatch(device -> device.getId().equals(id));
		if(isDeviceInAccount){
			deviceDoa.deleteById(id);
		} else throw new RuntimeException("Device with id (" + id + ") in account (" + usrNm + ") "
											+ "dose not exist");
	}

	public Device createDevice(String deviceName, String accountUsername) {
		Account account = getAccountByUsername(accountUsername);
		Device device = deviceDoa.save(new Device(deviceName));
		account.getDeviceList().add(device);
		accountDoa.save(account);
		return device;
		
	}

	public Device getById(String username, int id) {
		Account account = accountDoa.findByUsername(username);
		return account.getDeviceList().stream()
						.filter(p -> p.getId() == id)
						.findFirst()
						.get();
	}


	public AlarmEvent[] eventsByName(String name, String deviceName) {
		Account account = accountDoa.findByUsername(name); 
		
		List<Preset> presets = account.getPresetList();
		List<AlarmEvent> eventList = new ArrayList<>();
		
		presets.stream()
				.filter(prst -> prst.isActiveState())
				.forEach(prst ->
			{
				prst.getAlarmEventList().stream()
				.filter(evnt -> evnt.getDevice().getName().equals(deviceName))
				.forEach(evnt -> eventList.add(evnt));
			});
		return eventList.toArray(new AlarmEvent[eventList.size()]);
	}

	public List<Device> getAllDevices(String name) {
		return accountDoa.getByUsername(name).getDeviceList();
	}
}
