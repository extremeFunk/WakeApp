package io.rainrobot.wake.android.view.eventlist;


import android.support.design.widget.FloatingActionButton;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rainrobot.wake.android.activities.EventListActivity;
import io.rainrobot.wake.android.activities.GoBackActivity;
import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.android.configuration.AndroidView;
import io.rainrobot.wake.android.configuration.ContextMgr;
import io.rainrobot.wake.controller.EventListController;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.EventListView;

public class AndroidEventListView extends AndroidView<EventListActivity, EventListController>
        implements EventListView {

    private EventListItemAdapter listAdapter;
    private Map<Integer, Device> idToDeviceMap = new HashMap<>();

    public AndroidEventListView(ContextMgr contextMgr) {
        super(contextMgr, EventListActivity.class);
    }

    @Override
    public void show() {
        if (listAdapter != null) {
            listAdapter.clear();
        }
        super.show();
    }


    private void setAddCommand(Command command) {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.eventListAddBtn);
        fab.setOnClickListener((v) -> command.execute());
    }


    private void setGoBackCommand(Command command) {
        ((GoBackActivity) activity).setGoBackCommand(command);
    }

    @Override
    public void removeEvent(int id) {
        listAdapter.remove(id);
    }


    @Override
    public void addEvent(AlarmEvent e) {
        Device device = idToDeviceMap.get(e.getDevice().getId());
        e.setDevice(device);
        listAdapter.addEvent(e);
    }

    @Override
    public void updateEvent(AlarmEvent event) {

    }


    @Override
    protected void initializeActivity() {
        controller.getAllDevices((devices) -> {
            idToDeviceMap.clear();
            devices.forEach(d -> idToDeviceMap.put(d.getId(), d));
            setList(devices);
        });
        setAddCommand(controller.getAddCommand());
        setGoBackCommand(controller.getGoBackCmd());
    }

    private void setList(List<Device> devices) {
        controller.getAllEvents((events) -> {
            eventListConsumer(devices, events);
        });
    }

    private void eventListConsumer(List<Device> devices, List<AlarmEvent> events) {
        loadDevicesToEvents(devices, events);
        listAdapter = new EventListItemAdapter((EventListActivity) activity,
                                                events,
                                                createEventEditDialogFactory(devices),
                                                controller.getRemoveCommand());
        ListView rView = activity.findViewById(R.id.eventListRecyclerView);
        rView.setAdapter(listAdapter);
    }

    private void loadDevicesToEvents(List<Device> devices, List<AlarmEvent> events) {
        devices.forEach(d -> idToDeviceMap.put(d.getId(), d));
        events.forEach(e -> {
            Device d = idToDeviceMap.get(e.getDevice().getId());
            e.setDevice(d);
        });
    }

    private AlarmEventEditDialogFactory createEventEditDialogFactory(List<Device> devices) {
        return new AlarmEventEditDialogFactory(activity,
                controller.getUpdateCommand(), devices);
    }
}
