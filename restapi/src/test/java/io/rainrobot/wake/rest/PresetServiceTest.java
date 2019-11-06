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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import io.rainrobot.wake.rest.dto.Account;
import io.rainrobot.wake.rest.dto.AlarmEvent;
import io.rainrobot.wake.rest.dto.Preset;
import io.rainrobot.wake.rest.request.account.AccountDoa;
import io.rainrobot.wake.rest.request.alarmevent.AlarmEventDoa;
import io.rainrobot.wake.rest.request.preset.PresetDoa;
import io.rainrobot.wake.rest.request.preset.PresetService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
//@EnableAutoConfiguration
@SpringBootTest(classes = Service.class)
@DataJpaTest
//@EntityScan(basePackageClasses = {Account.class, Preset.class, AlarmEvent.class})
public class PresetServiceTest {

    @Autowired
    private TestEntityManager eMgr;

    @Autowired
    private AccountDoa accountDoa;
    @Autowired
    PresetDoa presetDoa;
    @Autowired
    AlarmEventDoa alarmEventDoa;

    private PresetService service;
    private Account account;

    @Before
    public void setUp() throws Exception {
        service = new PresetService(accountDoa,presetDoa, alarmEventDoa);

        account = new Account();
        account.setLastChange(new Date());
        Thread.sleep(1000);
        account.setUsername("usr");
        eMgr.persist(account);
    }

    @Test
    public void deletePreset() throws Exception{

        Preset orig = buildAndPersistPresetAndAlarm();
        Integer alarmId = orig.getAlarmEventList().iterator().next().getId();

        Preset pFound1 = eMgr.find(Preset.class, orig.getId());
        Assert.assertNotNull(pFound1);
        AlarmEvent eFound1 = eMgr.find(AlarmEvent.class,
                alarmId);
        Assert.assertNotNull(eFound1);
        Account aFound1 = eMgr.find(Account.class, account.getId());
        Assert.assertNotNull(aFound1);

        service.deletePreset(account.getUsername(), orig.getId());

        Preset pFound = eMgr.find(Preset.class, orig.getId());
        Assert.assertNull(pFound);
        AlarmEvent eFound = eMgr.find(AlarmEvent.class, alarmId);
        Assert.assertNull(eFound);
        Account aFound = eMgr.find(Account.class, account.getId());
        Assert.assertNotNull(aFound);
        //assert last change was altered
        assertLastChangeUpdated(aFound);

    }
    @Transactional
    private Preset buildAndPersistPresetAndAlarm() {
        eMgr.merge(account);
        Preset orig = new Preset();
        orig.setName("kobra");
        orig.setAccount(account);
        account.getPresetList().add(orig);
        AlarmEvent event = eMgr.persist(new AlarmEvent());
        orig.getAlarmEventList().add(event);
        eMgr.persist(orig);
        eMgr.persist(account);

        return orig;
    }

    private void assertLastChangeUpdated(Account aFound) {
        Date time1 = account.getLastChange();
        Date time2 = aFound.getLastChange();
        Assert.assertTrue(time1.before(time2));
    }

    @Test
    public void createPreset() {
        Preset created = null;

        created = service.createPreset(account.getUsername());

        Assert.assertNotNull(created);

        Account aFromDoa = eMgr.find(Account.class, account.getId());
        List<Preset> presetList = aFromDoa.getPresetList();
        Assert.assertEquals(1, presetList.size());
    }

    @Test
    public void getAllPresets() {
        Set<Preset> persisted = new HashSet<>();
        Preset orig = buildAndPersistPresetAndAlarm();
        Integer alarmId = orig.getAlarmEventList().iterator().next().getId();
        persisted.add(orig);
        Preset orig2 = buildAndPersistPresetAndAlarm();
        Integer alarmId2 = orig.getAlarmEventList().iterator().next().getId();
        persisted.add(orig2);
        eMgr.flush();
        eMgr.clear();

        List<Preset> fatched = service.getAllPresets(account.getUsername());
        Assert.assertTrue(fatched.containsAll(persisted));
    }

    @Test
    public void getById() {
    }

    @Test
    public void getEvents() {
    }
}