package io.rainrobot.wake.fx.view.itemlist;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.util.EventUtil;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.fx.view.dialog.EditDialog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class EventItem extends FxmlItem {
    @FXML
    Button editBtn;
    @FXML
    Button deleteBtn;
    @FXML
    Label delayLabel;
    @FXML
    Label snoozeLabel;
    @FXML
    Label shutoffLabel;
    @FXML
    Label soundLabel;
    @FXML
    Label volLabel;
    @FXML
    Label deviceLabel;

    private AlarmEvent event;

    private final Consumer<AlarmEvent> updateCommand;
    private final Consumer<Integer> removeCommand;
    private MainStageMgr mainWindowMgr;
    private final Map<Integer, Device> deviceMap = new HashMap<>();

    public EventItem(AlarmEvent event,
                     Consumer<AlarmEvent> updateCommand,
                     Consumer<Integer> removeCommand,
                     List<Device> devices, MainStageMgr mainWindowMgr) {
        super();
        this.updateCommand = updateCommand;
        this.removeCommand = removeCommand;
        this.mainWindowMgr = mainWindowMgr;
        setMap(devices);
        this.event = event;
    }

    private void setMap(List<Device> devices) {
        devices.forEach(dev -> deviceMap.put(dev.getId(), dev));
    }

    @Override
    public Integer getId() {
        return event.getId();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deleteBtn.setOnAction((e) -> removeCommand.accept(event.getId()));
        editBtn.setOnAction((e) -> showEditDialog());
        setText(event);
    }

    public void update(AlarmEvent e) {
        event = e;
        setText(e);
    }

    private void setText(AlarmEvent e) {
        //delay
        delayLabel.setText(EventUtil.delayText(e));
        //shutoff
        shutoffLabel.setText((EventUtil.shutoffText(e)));
        //snooze
        snoozeLabel.setText((EventUtil.snoozeText(e)));
        //vol
        volLabel.setText((EventUtil.volText(e)));
        //sound
        soundLabel.setText((EventUtil.soundText(e)));
        //device
        deviceLabel.setText(getDeviceName());
    }

    private String getDeviceName() {
        //This event has a dummy device
        //getDeviceByName retrieve the
        //actual device
        int id = event.getDevice().getId();
        return deviceMap.get(id).getName();
    }

    private void showEditDialog() {
        EditDialog dialog = new EditDialog(event, updateCommand, deviceMap, mainWindowMgr);
        dialog.show();
    }

    @Override
    protected URL getUrl() {
        return getClass().getResource("/fxml/item/event_item.fxml");
    }

}
