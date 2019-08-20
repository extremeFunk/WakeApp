package io.rainrobot.wake.rest.request.preset;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.rainrobot.wake.core.Path;
import io.rainrobot.wake.rest.configuration.appuser.AppAuth;
import io.rainrobot.wake.core.Preset;

@RestController()
public class PresetController {
	
	@Autowired
	private PresetService service;
	
	@PutMapping(Path.PRESET)
	public void updatePreset(@AuthenticationPrincipal AppAuth auth,
								@RequestBody Preset preset) {
		service.savePreset(preset, auth.getName());

	}	

	@DeleteMapping(value = Path.PRESET + "/{id}")
	public void deletePreset(@AuthenticationPrincipal AppAuth auth, @PathVariable int id) {
		service.deletePreset(auth.getName(), id);
	}	

	
	@PostMapping(Path.PRESET)
	public Preset createPreset(@AuthenticationPrincipal AppAuth auth) {
		return service.createPreset(auth.getName());
	}	

	@GetMapping(Path.PRESET)
	public List<Preset> getAllPreset(@AuthenticationPrincipal AppAuth auth) {
		List<Preset> allPresets = service.getAllPresets(auth.getName());
		return allPresets;
	}	
	
	@GetMapping(value = Path.PRESET +"/{id}")
	public Preset getPreset(@AuthenticationPrincipal AppAuth auth, 
								@PathVariable int id) {
		return service.getById(auth.getName(), id);
	}	
}
