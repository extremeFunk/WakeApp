package io.rainrobot.wake.rest.request.alarmevent;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.Preset;

@Transactional
public class AlarmEventCustomDoaImpl implements AlarmEventCustomDoa {

    @PersistenceContext
    EntityManager eMgr;

    @Override
    public <S extends AlarmEvent> S deepSave(S event) {
        eMgr.persist(event);
        Device device = event.getDevice();
        device.internalAddAlarmEvent(event);
        eMgr.persist(device);
        Preset preset = event.getPreset();
        preset.internalAddAlarmEvent(event);
        eMgr.persist(preset);
        eMgr.flush();
        return event;

    }
}
