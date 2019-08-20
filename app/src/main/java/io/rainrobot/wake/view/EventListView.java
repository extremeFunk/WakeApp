package io.rainrobot.wake.view;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.rainrobot.wake.app.IView;
import io.rainrobot.wake.controller.EventListController;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Sound;
import io.rainrobot.wake.util.Command;

public interface EventListView extends IView<EventListController> {
	void removeEvent(int id);
	void addEvent(AlarmEvent e);
	void updateEvent(AlarmEvent event);
}
