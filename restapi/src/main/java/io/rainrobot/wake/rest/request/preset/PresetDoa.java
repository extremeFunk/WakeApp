package io.rainrobot.wake.rest.request.preset;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.rainrobot.wake.rest.dto.Preset;

@Repository
public interface PresetDoa extends JpaRepository<Preset, Integer> {
	List<Preset> findAll();

    Preset findByName(String name);

}
