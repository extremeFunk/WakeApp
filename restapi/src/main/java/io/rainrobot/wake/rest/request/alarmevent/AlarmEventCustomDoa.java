package io.rainrobot.wake.rest.request.alarmevent;

import io.rainrobot.wake.core.AlarmEvent;

public interface AlarmEventCustomDoa {
    <S extends AlarmEvent> S deepSave(S event);
}
