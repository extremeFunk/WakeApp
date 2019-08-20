package io.rainrobot.wake.rest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.rainrobot.wake.core.Account;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.core.Sound;
import io.rainrobot.wake.rest.request.alarmevent.AlarmEventDoa;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
//@EnableAutoConfiguration
@SpringBootTest(classes = Service.class)
@DataJpaTest
//@EntityScan(basePackageClasses = {Account.class, Preset.class, AlarmEvent.class})
public class EventDoaTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AlarmEventDoa alarmEventDoa;

    private Account account;
    private AlarmEvent event;
    private Preset preset;
    private Device device;
    private Set<AlarmEvent> aList;


    @Before
    public void setUp() throws Exception {
        account = new Account();
        account.setUsername("a1");

        event = new AlarmEvent();
        event.setDelay(1);

        aList = new HashSet<>();
        aList.add(event);

        device = new Device();
        device.setName("d1");

        preset = new Preset();
        preset.setName("p1");

        preset.setAccount(account);
    }



//    private void persistPresetAndDeviceWithEvent() {
//        preset.setAlarmEventList(aList);
//        device.setEventList(new HashSet<>(aList));
//        persistOnlyPreset();
//        persistOnlyDevice();
//    }

    private void persistOnlyDevice() {
        entityManager.persistAndFlush(device);
        entityManager.clear();

    }

    private void persistOnlyEvent() {
        entityManager.persistAndFlush(event);
        entityManager.clear();
    }

    private void persistOnlyPreset() {
        entityManager.persistAndFlush(preset);
        entityManager.clear();
    }

    private void persistAccount() {
        entityManager.persistAndFlush(account);
        entityManager.clear();
    }

    @Test
    public void throwNoPresetError() {
        Exception ex = null;
        event.setPreset(preset);
        try {
            alarmEventDoa.save(event);
        } catch (IllegalStateException e) {
            ex = e;
        } finally {
            System.out.println("\n" + ex + "\n");
            Assert.assertNotNull(ex);
        }

    }

    @Test
    public void eventSave() {
        persistAll();
//        alarmEventDoa.save(preset);
        AlarmEvent eFound = entityManager.find(AlarmEvent.class, event.getId());
        Assert.assertEquals(event, eFound);

        System.out.println("\n" + eFound + "\n");
    }

    private void persistAll() {
        persistAccount();
        event.setPreset(preset);
        persistOnlyPreset();
        event.setDevice(device);
        persistOnlyDevice();
        alarmEventDoa.save(event);
    }

    @Test
    public void presetDelete() {
        persistAll();

        alarmEventDoa.deleteById(event.getId());

        AlarmEvent eFound = entityManager.find(AlarmEvent.class, event.getId());
        System.out.println("\n" + eFound + "\n");
        Assert.assertNull(eFound);

        Preset pFound = entityManager.find(Preset.class, preset.getId());
        System.out.println("\n" + pFound + "\n");
        Assert.assertNotNull(pFound);
        int presetEventSize = preset.getAlarmEventList().size();
        Assert.assertEquals(0, presetEventSize);

        Device dFound = entityManager.find(Device.class, device.getId());
        System.out.println("\n" + dFound + "\n");
        Assert.assertNotNull(pFound);
        int deviceEventSize = device.getEventList().size();
        Assert.assertEquals(0, presetEventSize);
    }

    @Test
    public void presetUpdateName() {
        event.setSound(Sound.BIRDS);
        persistAll();

        Sound nuSound = Sound.BELL;
        event.setSound(nuSound);
        AlarmEvent pFound = alarmEventDoa.findById(event.getId()).get();
        Assert.assertEquals(nuSound, pFound.getSound());
    }

}