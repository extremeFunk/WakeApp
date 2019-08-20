package io.rainrobot.wake.alarm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.rainrobot.wake.app.IEventDoa;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.util.DateUtil;
import io.rainrobot.wake.util.Maps;


public class AlarmUpdater {

    private IAlarmMgr alarmMgr;
    private IEventDoa eventDoa;

    public AlarmUpdater(IAlarmMgr alarmMgr, IEventDoa eventDoa) {
        this.alarmMgr = alarmMgr;
        this.eventDoa = eventDoa;
    }

    public void update(AlarmEvent[] updateEvents) {
        AlarmEvent[] fromDb = eventDoa.get();
        setToAlarmMgr(fromDb, updateEvents);
        eventDoa.clear();
        eventDoa.set(updateEvents);
    }

    public void setToAlarmMgr(AlarmEvent[] fromDb, AlarmEvent[] updateEvents) {
        Map<Integer, AlarmEvent> current = Maps.getIdeabelMap(fromDb);
        Map<Integer, AlarmEvent> updated = Maps.getIdeabelMap(updateEvents);

        List<AlarmEvent> toRemove = getToRemoveList(current, updated);
//        List<AlarmEvent> toAdd = getToAdd(current, updated);

        //remove
        for (AlarmEvent e : toRemove) { alarmMgr.removeAlarm(e); }
        //plusMinuets
        for (AlarmEvent e : updateEvents) { alarmMgr.addAlarm(e, procTime(e.getTime())); }
    }

    private Date procTime(Date time) {
        return DateUtil.getNextTime(time);
    }


    private List<AlarmEvent> getToAdd(Map<Integer, AlarmEvent> current, Map<Integer, AlarmEvent> updated) {
        List<AlarmEvent> list = new ArrayList<>();
        updated.forEach((id, event) -> {
            //if event is new
            if (!current.containsKey(id)) {
               list.add(event);
            } else {
                if(!compareEvents(event, current.get(id))) {
                    list.add(event);
                }
            }
        });
        return list;
    }

    private List<AlarmEvent> getToRemoveList(Map<Integer, AlarmEvent> current, Map<Integer, AlarmEvent> updated) {
        List<AlarmEvent> list = new ArrayList<>();
        current.forEach((id, event) -> {
            //if event no longer exist
            if (!updated.containsKey(id)) {
                list.add(event);
            } else {
                //if event has changed
                if(!compareEvents(event, updated.get(id))){
                    list.add(event);
                }
            }
        });
        return list;
    }

    public boolean compareEvents(AlarmEvent e1, AlarmEvent e2) {
        return e1.getDelay() == e2.getDelay() &&
                e1.getVol() == e2.getVol() &&
                e1.getSnooze() == e2.getSnooze() &&
                e1.getShutOff() == e2.getShutOff() &&
                e1.getSound() == e2.getSound() &&
                e1.getTime().equals(e2.getTime());
    }

    public void clear() {
        AlarmEvent[] events = eventDoa.get();
        for (AlarmEvent e : events) {
            alarmMgr.removeAlarm(e);
        }
    }
}
