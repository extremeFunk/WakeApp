package io.rainrobot.wake.rest.request.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.rainrobot.wake.rest.dto.Device;

@Repository
public interface DeviceDoa extends JpaRepository<Device, Integer> {

}
