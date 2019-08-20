package io.rainrobot.wake.fx.view;

import io.rainrobot.wake.controller.EventListController;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.util.Log;
import io.rainrobot.wake.fx.Res;
import io.rainrobot.wake.fx.view.component.FxView;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.fx.view.itemlist.EventItem;
import io.rainrobot.wake.fx.view.itemlist.ItemList;
import io.rainrobot.wake.view.EventListView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FxEventListView extends FxView<EventListController> implements EventListView {
    @FXML
    Button addBtn;
    @FXML
    Button goBackBtn;
    @FXML
    ScrollPane scrollPane;
    @FXML
    ItemList<EventItem> itemBox;

    private EventListController ctrl;
    private List<Device> deviceList;

    public FxEventListView(MainStageMgr loader) {
        super(loader);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.getStylesheets().add(Res.getCss("Main"));
        addBtn.setOnAction((e) -> ctrl.getAddCommand().execute());
        goBackBtn.setOnAction((e) -> {
            itemBox.clear();
            ctrl.getGoBackCmd().execute();
        });
        ctrl.getAllEvents((eventList) -> addAllEvents(eventList));
    }

    private void addAllEvents(List<AlarmEvent> eventList) {
        ctrl.getAllDevices(devices -> {
            this.deviceList = devices;
            eventList.forEach(e -> addEvent(e));
        });
    }

    @Override
    protected String getFxmlName() {
        return "event_list";
    }

    @Override
    public void setController(EventListController controller) {
        this.ctrl = controller;
    }

    @Override
    public void removeEvent(int id) {
        itemBox.remove(id);
    }

    @Override
    public void addEvent(AlarmEvent e) {
        if(!itemBox.containsId(e.getId())) {
            Log.i("event item box already contains event with id " + e.getId());
            itemBox.add(createItem(e));
        }
    }

    @Override
    public void updateEvent(AlarmEvent event) {
        EventItem item = itemBox.getItem(event.getId());
        item.update(event);
    }

    private EventItem createItem(AlarmEvent event) {
        return new EventItem(event,
                ctrl.getUpdateCommand(),
                ctrl.getRemoveCommand(),
                deviceList,
                mainWindowMgr);
    }
}
