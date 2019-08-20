package io.rainrobot.wake.app;

import io.rainrobot.wake.core.AlarmEvent;

public interface IEventDoa {
    void set(AlarmEvent[] events);

    AlarmEvent[] get();

    void clear();
}
