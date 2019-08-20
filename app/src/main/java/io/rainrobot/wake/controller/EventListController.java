package io.rainrobot.wake.controller;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.rainrobot.wake.app.ControllerMgr;
import io.rainrobot.wake.app.ControllerWithId;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.Sound;
import io.rainrobot.wake.model.EventListModel;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.EventListView;

public class EventListController extends ControllerWithId<EventListView, EventListModel> {


	public EventListController(EventListView view,
							   EventListModel model,
							   ControllerMgr controllerMgr,
							   ASyncProvider aSyncProvider) {

		super(view, model, controllerMgr, aSyncProvider);
	}


	public void getAllEvents(Consumer<List<AlarmEvent>> consumer) {
		asyncCall((
				() -> model.getAllEvents(controllerId)),
				consumer);
	}



	public Command getAddCommand() {
		return () -> {
				asyncCall(
						(() -> model.createEvent(controllerId)),
						(event) -> view.addEvent(event));
		};
	}

	public Command getGoBackCmd() {
		return controllerMgr::showPresets;
	}

	public Consumer<Integer> getRemoveCommand() {
		return (id) -> {
			asyncCall(
					(() -> model.removeEvent(id)),
					(() -> view.removeEvent(id)));
		};
	}


	public Consumer<AlarmEvent> getUpdateCommand() {
		return ((event) -> {
			asyncCall(() -> model.updateEvent(event),
					() -> view.updateEvent(event));
		});
	}

	public void getAllDevices(Consumer<List<Device>> consumer) {
		asyncCall(() -> model.getAllDevices(controllerId),
					(devices) -> consumer.accept(devices));
	}
}
