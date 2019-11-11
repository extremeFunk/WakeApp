package io.rainrobot.wake.core.json;

import io.rainrobot.wake.core.AlarmEvent;

public class AlarmEventDeSerializer extends IdabelDeSerializer<AlarmEvent> {
	@Override
	protected AlarmEvent getInstance() { return new AlarmEvent(); }
}
