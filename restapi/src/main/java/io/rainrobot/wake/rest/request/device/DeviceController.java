package io.rainrobot.wake.rest.request.device;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.rainrobot.wake.core.Path;
import io.rainrobot.wake.rest.configuration.appuser.AppAuth;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;


@RestController()
public class DeviceController {
	
	@Autowired
	private DeviceService service;

	@PutMapping(value = Path.DEVICE)
	public void updateDevice(@RequestBody Device device) {
		service.saveDevice(device);
	}	

	@DeleteMapping(value = Path.DEVICE + "/{id}")
	public void deleteDevice(@AuthenticationPrincipal AppAuth auth, @PathVariable int id) {
		service.deleteDevice(auth.getName(), id);
	}	
	
	@PostMapping(value = Path.DEVICE)
	public Device createDevice(@AuthenticationPrincipal AppAuth user) {
		return service.createDevice("defaultName", user.getName());
	}

	@GetMapping(value = Path.DEVICE+ "/{id}")
	public Device getDeviceById(@AuthenticationPrincipal AppAuth auth, 
								@PathVariable int id) {
		return service.getById(auth.getName(), id);
	}	

	@GetMapping(value = Path.DEVICE + Path.ALARMEVENT + "/{deviceName}")
	public AlarmEvent[] getEventsByName(@AuthenticationPrincipal AppAuth auth, 
										@PathVariable String deviceName) {
		return service.eventsByName(auth.getName(), deviceName);
	}

	@GetMapping(value = Path.DEVICE)
	public List<Device> getAllDevice(@AuthenticationPrincipal AppAuth auth) {
		return service.getAllDevices(auth.getName());
	}
}
