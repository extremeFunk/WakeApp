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
////            (unitName = "deviceEntityManager")
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

//        device.internalRemoveDevice(event);
//        preset.internalRemoveDevice(event);
//        eMgr.flush();

        return event;

//        Device oldDevice = eMgr.find(Device.class, event.getDevice().getId());
//        eMgr.find(AlarmEvent.class, event.getId());
//        eMgr.refresh(event);
//        eMgr.persist(event);
//        Device nuDevice = eMgr.find(Device.class, event.getDevice().getId());
//
////        event.setDevice(device);
//        if(event.getDevice().getId() != nuDevice.getId()) {
//            for(AlarmEvent e : oldDevice.getEventList()) {
//                if(e.getId() == event.getId()) {
//                    oldDevice.getEventList().remove(e);
//                    break;
//                }
//            }
//            eMgr.persist(oldDevice);
//        }
//        event.setDevice(nuDevice);
//        nuDevice.getEventList().add(event);
//        eMgr.refresh(event);
//        eMgr.persist(nuDevice);
//        return event;
    }
}
