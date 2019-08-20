package io.rainrobot.wake.view;

import io.rainrobot.wake.app.IView;
import io.rainrobot.wake.controller.DeviceEventsController;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.util.Command;

public interface DeviceEventsView extends IView<DeviceEventsController> {

	void setEvents(AlarmEvent[] events);


}
