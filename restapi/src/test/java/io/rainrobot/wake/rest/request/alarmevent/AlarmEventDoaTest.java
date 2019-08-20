package io.rainrobot.wake.rest.request.alarmevent;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.rest.JpaConfig;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.rest.Service;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
@EnableTransactionManagement
//@EnableAutoConfiguration
@SpringBootTest(classes = Service.class)
@DataJpaTest
public class AlarmEventDoaTest {

    @Autowired
    private TestEntityManager eMgr;
    @Autowired
    private AlarmEventDoa eventDoa;

    @Test
    public void createEvent() {
        Preset persistPreset = new Preset();
        persistPreset.setName("some preset");
        Preset pFound = eMgr.persistFlushFind(persistPreset);
        eMgr.flush();
//        Preset pojoPreset = new Preset();
//        pojoPreset.setId(persistPreset.getId());
//        pojoPreset.setName(persistPreset.getName());
//        eMgr.clear();
        //event
//        pFound.setId(99);
        eMgr.merge(pFound);
        AlarmEvent persist = new AlarmEvent(pFound);
        eventDoa.saveAndFlush(persist);
        int delay = 3;
        persist.setDelay(delay);
        saveEventToDao(persist);
//        eMgr.clear();

        Iterable<AlarmEvent> all = eventDoa.findAll();
        Assert.assertTrue(all.iterator().hasNext());

        AlarmEvent found = all.iterator().next();
        Assert.assertEquals(persist, found);

        Assert.assertEquals(delay, found.getDelay());
        Assert.assertEquals(pFound, found.getPreset());
    }

    private AlarmEvent saveEventToDao(AlarmEvent persist) {
        return persistEvent(persist);
    }

    private AlarmEvent persistEvent(AlarmEvent persist) {
        return eventDoa.save(persist);
    }

    @Test
    public void findAllByPreset() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void findByDevice() {
    }
}