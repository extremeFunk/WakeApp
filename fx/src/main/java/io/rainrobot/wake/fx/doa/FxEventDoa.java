package io.rainrobot.wake.fx.doa;

import io.rainrobot.wake.app.IEventDoa;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.fx.doa.HibernateExecuter;
import io.rainrobot.wake.fx.doa.entity.AlarmEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.function.Consumer;

public class FxEventDoa implements IEventDoa {

    private final HibernateExecuter<List<AlarmEntity>> exc;

    public FxEventDoa(HibernateExecuter hibernateExecuter) {
        this.exc = hibernateExecuter;
    }

    @Override
    public void set(AlarmEvent[] events) {
        for (AlarmEvent e : events) {
            exc.execute(session -> {
                session.saveOrUpdate(new AlarmEntity(e));
            });
        }
    }

    @Override
    public AlarmEvent[] get() {
        List<AlarmEntity> entList = exc.execute(
                session -> {
             return session.createQuery("from AlarmEntity").list();
        });

        AlarmEvent[] eventList = convertEntityToPojo(entList);
        return eventList;
    }

    private AlarmEvent[] convertEntityToPojo(List<AlarmEntity> entList) {
        if (entList == null) return new AlarmEvent[0];
        AlarmEvent[] eventList  = new AlarmEvent[entList.size()];
        int i = 0;
        for (AlarmEntity ent : entList) {
            eventList[i] = ent.getEvent();
            i++;
        }
        return eventList;
    }

    @Override
    public void clear() {
        exc.execute(session -> {
            Query query = session.createQuery("delete from AlarmEntity");
            query.executeUpdate();
        });
    }
}
