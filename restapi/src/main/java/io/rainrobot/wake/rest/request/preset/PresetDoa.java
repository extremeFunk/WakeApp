package io.rainrobot.wake.rest.request.preset;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Preset;

@Repository
public interface PresetDoa extends JpaRepository<Preset, Integer> {
	List<Preset> findAll();

    Preset findByName(String name);

}
