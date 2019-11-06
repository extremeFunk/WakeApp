package io.rainrobot.wake.rest.request.alarmevent;

import io.rainrobot.wake.rest.dto.AlarmEvent;

public interface AlarmEventCustomDoa {
    <S extends AlarmEvent> S deepSave(S event);
}
