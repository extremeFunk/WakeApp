package io.rainrobot.wake.rest.request.alarmevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.rainrobot.wake.rest.dto.Path;
import io.rainrobot.wake.rest.configuration.appuser.AppAuth;
import io.rainrobot.wake.rest.dto.AlarmEvent;

@RestController()
@RequestMapping(Path.ALARMEVENT)
public class AlarmEventController {
	
	@Autowired
	private AlarmEventService service;
	
	@DeleteMapping(value = "/{id}")
	public void deleteAlarmEvent(@AuthenticationPrincipal AppAuth auth,
								   @PathVariable int id) {
		service.deleteAlarmEvent(auth.getName(), id);
	}	
	
	@PutMapping()
	public void updateAlarmEvent(@AuthenticationPrincipal AppAuth auth,
								 @RequestBody AlarmEvent alarmEvent) {
		service.updateAlarmEvent(auth.getName(), alarmEvent);

	}	

	@PostMapping(value = Path.PRESET + "/{preset_id}")
	public AlarmEvent createAlarmEvent(@AuthenticationPrincipal AppAuth auth,
										@PathVariable Integer preset_id) {
		return service.createAlarmEvent(auth.getName(), preset_id.intValue());
	}

	@GetMapping(value = Path.PRESET + "/{preset_id}")
	public List<AlarmEvent> getAllAlarmEvents(@PathVariable Integer preset_id) {
		return service.getAllAlarmEvents(preset_id.intValue());

	}	
}
