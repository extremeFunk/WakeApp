package io.rainrobot.wake.rest.request.alarmevent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.Preset;

@Repository
public interface AlarmEventDoa extends JpaRepository<AlarmEvent, Integer>,
										AlarmEventCustomDoa {

	List<AlarmEvent> findAllByPreset(Preset preset);
	List<AlarmEvent> findAllByDevice(Device device);
	void deleteById(int id);
	AlarmEvent[] findByDevice(Device device);
    AlarmEvent getById(Integer id);
}
