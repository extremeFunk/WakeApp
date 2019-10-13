package io.rainrobot.wake.rest.request.alarmevent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.rainrobot.wake.core.Account;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.Sound;
import io.rainrobot.wake.rest.JpaConfig;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.rest.Service;
import io.rainrobot.wake.rest.request.account.AccountDoa;
import io.rainrobot.wake.rest.request.device.DeviceDoa;
import io.rainrobot.wake.rest.request.preset.PresetDoa;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
//@EnableAutoConfiguration
@SpringBootTest(classes = Service.class)
@DataJpaTest
public class AlarmEventServiceTest {

    @Autowired
    private TestEntityManager eMgr;

    @Autowired
    private AccountDoa accountDoa;
    @Autowired
    PresetDoa presetDoa;
    @Autowired
    AlarmEventDoa alarmEventDoa;
    @Autowired
    private DeviceDoa deviceDoa;

    private AlarmEventService service;
    private String usrNm;
    private Account aCommint;
    private Device d1Commit;
    private Device d2Commit;
    private Preset pCommit;
    private AlarmEvent eCommit;


    @Before
    public void setUp() throws Exception {
        service = new AlarmEventService(accountDoa, presetDoa, alarmEventDoa, deviceDoa);

        Device device = new Device();
        d1Commit = eMgr.persist(device);

        Device device2 = new Device();
        d2Commit = eMgr.persist(device2);

        usrNm = "usr";
        Account account = new Account();
        account.setUsername(usrNm);
        account.getDeviceList().add(d1Commit);
        account.getDeviceList().add(d2Commit);
        aCommint = eMgr.persist(account);

        Preset preset = new Preset();
        pCommit = eMgr.persist(preset);

        AlarmEvent evetn = new AlarmEvent();
        evetn.setDevice(d1Commit);
        evetn.setPreset(pCommit);
        evetn.setSound(Sound.BELL);
        eCommit = eMgr.persist(evetn);

        eMgr.flush();
        eMgr.clear();

    }

    @Test
    public void updateAlarmEvent() {

        //set
        AlarmEvent eToSave = new AlarmEvent();
        eToSave.setId(eCommit.getId());
        Sound nuSound = Sound.RING;
        eToSave.setSound(nuSound);
        eToSave.setDevice(d2Commit);
        eToSave.setPreset(pCommit);
        //do
        service.updateAlarmEvent(usrNm, eToSave);
        //assert
        AlarmEvent eFound = eMgr.find(AlarmEvent.class, eCommit.getId());
        assertEquals(nuSound, eFound.getSound());

        Device d1Found = eMgr.find(Device.class, d1Commit.getId());
        assertFalse(d1Found.getEventList().contains(eToSave));

        Device d2Found = eMgr.find(Device.class, d2Commit.getId());
        assertTrue(d2Found.getEventList().contains(eToSave));

        Preset pFound = eMgr.find(Preset.class, pCommit.getId());
        assertTrue(pFound.getAlarmEventList().contains(eToSave));

        Account aFound = eMgr.find(Account.class, aCommint.getId());
        assertNotNull(aFound.getLastChange());
    }

    @Test
    public void deleteAlarmEvent() {
        AlarmEvent eCommit = new AlarmEvent();
        eCommit.setDevice(d1Commit);
        eCommit.setPreset(pCommit);
        eCommit.setSound(Sound.BELL);
        eMgr.persist(eCommit);

        eMgr.flush();
        eMgr.clear();

        service.deleteAlarmEvent(usrNm, eCommit.getId());

        AlarmEvent eFound = eMgr.find(AlarmEvent.class, aCommint.getId());

        assertNull(eFound);

        Preset pFound = eMgr.find(Preset.class, pCommit.getId());
        assertFalse(pFound.getAlarmEventList().contains(aCommint));

        Device dFound = eMgr.find(Device.class, d1Commit.getId());
        assertFalse(dFound.getEventList().contains(aCommint));
    }

    @Test
    public void createAlarmEvent() {
        AlarmEvent eCreated = service.createAlarmEvent(usrNm, pCommit.getId());
        assertNotNull(eCreated);

        AlarmEvent found = eMgr.find(AlarmEvent.class, eCreated.getId());
        assertNotNull(found);

        Device dFound = eMgr.find(Device.class, found.getDevice().getId());
        boolean flag = dFound.getEventList()
                            .stream()
                            .anyMatch(e -> e.equals(eCreated));
        assertTrue(flag);

        Account aFound = eMgr.find(Account.class, aCommint.getId());
        assertNotNull(aFound.getLastChange());

        Preset pFound = eMgr.find(Preset.class, pCommit.getId());
        boolean flag2 = pFound.getAlarmEventList()
                .stream()
                .anyMatch(e -> e.equals(eCreated));
        assertTrue(flag2);

    }

    @Test
    public void getAllAlarmEvents() {
    }
}