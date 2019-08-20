package io.rainrobot.wake.rest.request.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.Set;

import io.rainrobot.wake.core.Account;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.Preset;

@Repository
public interface DeviceDoa extends JpaRepository<Device, Integer> {

}
