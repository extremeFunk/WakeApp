package io.rainrobot.wake.android.configuration;

import java.util.List;

import io.rainrobot.wake.android.eventdatabase.AlarmEntity;
import io.rainrobot.wake.android.eventdatabase.AlarmRoomDao;
import io.rainrobot.wake.app.IEventDoa;
import io.rainrobot.wake.core.AlarmEvent;

public class AndroidEventDoa implements IEventDoa {

    private AlarmRoomDao dataBase;

    public AndroidEventDoa(AlarmRoomDao dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void set(AlarmEvent[] events) {
        dataBase.insertAll(AlarmEntityConverter.toEntity(events));
    }

    @Override
    public AlarmEvent[] get() {
        return AlarmEntityConverter.toObj(dataBase.getAll());
    }

    @Override
    public void clear() {
        dataBase.clear();
    }

    private static class AlarmEntityConverter {
        public static AlarmEntity[] toEntity(AlarmEvent[] objs) {
            AlarmEntity[] entities = new AlarmEntity[objs.length];
            for (int i = 0; i<objs.length; i++) {
                entities[i] = new AlarmEntity(objs[i]);
            }
            return entities;
        }

        public static AlarmEvent[] toObj(List<AlarmEntity> entities) {
            AlarmEvent[] objs = new AlarmEvent[entities.size()];
            for (int i = 0; i<entities.size(); i++) {
                objs[i] = entities.get(i).toObj();
            }
            return objs;
        }
    }
}
