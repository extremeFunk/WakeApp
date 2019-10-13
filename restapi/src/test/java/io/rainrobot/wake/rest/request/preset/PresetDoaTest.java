package io.rainrobot.wake.rest.request.preset;

import io.rainrobot.wake.rest.JpaConfig;
import io.rainrobot.wake.rest.Service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import io.rainrobot.wake.core.Account;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.rest.request.preset.PresetDoa;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes =  JpaConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class PresetDoaTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    PresetDoa presetDoa;


    private Account account;
    private AlarmEvent event;
    private Preset preset;


    @Before
    public void setUp() throws Exception {
        account = new Account();
        account.setUsername("a1");

        event = new AlarmEvent();
        event.setDelay(1);
        Set<AlarmEvent> aList = new HashSet();
        aList.add(event);

        preset = new Preset();
        preset.setName("p1");
//        preset.setAlarmEventList(aList);
        preset.setAccount(account);
    }

    private void persistPresetWithEvent() {
        entityManager.persistAndFlush(preset);
        entityManager.clear();
    }

    private void persistAccount() {
        entityManager.persistAndFlush(account);
        entityManager.clear();
    }

    @Test
    public void presetThrowNoAccountError() {
        Exception ex = null;
        try {
            presetDoa.save(preset);
        } catch (IllegalStateException e) {
            ex = e;
        } finally {
            Assert.assertNotNull(ex);
            System.out.println("\n" + ex + "\n");
        }

    }

    @Test
    public void presetSave() {
        persistAccount();
        Date date = new Date();
        preset.setTime(date);
        presetDoa.save(preset);
        Preset pFound = entityManager.find(Preset.class, preset.getId());
        Assert.assertEquals(preset, pFound);
        Assert.assertEquals(date, pFound.getTime());

        System.out.println("\n" + date + "\n");
        System.out.println("\n" + pFound.getTime() + "\n");


    }

    @Test
    public void presetDelete() {
        persistAccount();
        persistPresetWithEvent();

        presetDoa.deleteById(preset.getId());

        Preset pFound = entityManager.find(Preset.class, preset.getId());
        Assert.assertNull(pFound);
        AlarmEvent eFound = entityManager.find(AlarmEvent.class, event.getId());
        Assert.assertNull(eFound);
        Account aFound = entityManager.find(Account.class, account.getId());
        Assert.assertNotNull(aFound);
        int accountPresetSize = aFound.getPresetList().size();
        Assert.assertEquals(0, accountPresetSize);

        System.out.println("\n" + pFound + "\n");
        System.out.println("\n" + eFound + "\n");
        System.out.println("\n" + aFound + "\n");
    }

    @Test
    public void presetUpdateName() {
        persistAccount();
        persistPresetWithEvent();
        String nuName = "nuName";
        preset.setName("nuName");
        presetDoa.save(preset);

        Preset pFound = entityManager.find(Preset.class, this.preset.getId());
        Assert.assertEquals(pFound.getName(), nuName);
    }

}