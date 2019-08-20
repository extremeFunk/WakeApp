package io.rainrobot.wake.fx.view;

import io.rainrobot.wake.controller.DeviceEventsController;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.util.EventUtil;
import io.rainrobot.wake.fx.view.component.FxView;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.core.util.DateUtil;
import io.rainrobot.wake.view.DeviceEventsView;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class FxDeviceEventView extends FxView<DeviceEventsController> implements DeviceEventsView {
    public static final String DEFAULT_NO_EVENT_MGS = "No Schedule events";
    @FXML
    Button goBackBtn;
    @FXML
    TableView<EventTableItem> table;
    @FXML
    TableColumn<EventTableItem, String> volCol;
    @FXML
    TableColumn<EventTableItem, String> timeCol;
    @FXML
    TableColumn<EventTableItem, String> soundCol;
    @FXML
    TableColumn<EventTableItem, String> snoozeCol;
    @FXML
    TableColumn<EventTableItem, String> shutoffCol;

    private DeviceEventsController ctrl;

    public FxDeviceEventView(MainStageMgr loader) {
        super(loader);
    }

    @Override
    protected String getFxmlName() {
        return "device_events";
    }

    @Override
    public void setController(DeviceEventsController controller) {
        this.ctrl = controller;
    }


    @Override
    public void setEvents(AlarmEvent[] events) {
        for (AlarmEvent e : events) {
            table.getItems().add(new EventTableItem(e));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setPlaceholder(new Label(DEFAULT_NO_EVENT_MGS));
        goBackBtn.setOnAction(e -> ctrl.getGoBackCmd().execute());
        setUpTable();
        ctrl.injectDataToView();
    }

    private void setUpTable() {
        timeCol.setCellValueFactory(new PropertyValueFactory("time"));
        soundCol.setCellValueFactory(new PropertyValueFactory("sound"));
        volCol.setCellValueFactory(new PropertyValueFactory("vol"));
        snoozeCol.setCellValueFactory(new PropertyValueFactory("snooze"));
        shutoffCol.setCellValueFactory(new PropertyValueFactory("shutoff"));
    }

    public static class EventTableItem {
        private final SimpleStringProperty time;
        private final SimpleStringProperty sound;
        private final SimpleStringProperty vol;
        private final SimpleStringProperty snooze;
        private final SimpleStringProperty shutoff;

        public EventTableItem(AlarmEvent event) {
            time = new SimpleStringProperty(DateUtil.toHrMnTxt(event.getTime()));
            sound = new SimpleStringProperty(event.getSound().name());
            vol = new SimpleStringProperty(EventUtil.volText(event));
            snooze = new SimpleStringProperty(EventUtil.snoozeText(event));
            shutoff = new SimpleStringProperty(EventUtil.snoozeText(event));
        }

        public String getTime() {
            return time.get();
        }

        public SimpleStringProperty timeProperty() {
            return time;
        }

        public void setTime(String time) {
            this.time.set(time);
        }

        public String getSound() {
            return sound.get();
        }

        public SimpleStringProperty soundProperty() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound.set(sound);
        }

        public String getVol() {
            return vol.get();
        }

        public SimpleStringProperty volProperty() {
            return vol;
        }

        public void setVol(String vol) {
            this.vol.set(vol);
        }

        public String getSnooze() {
            return snooze.get();
        }

        public SimpleStringProperty snoozeProperty() {
            return snooze;
        }

        public void setSnooze(String snooze) {
            this.snooze.set(snooze);
        }

        public String getShutoff() {
            return shutoff.get();
        }

        public SimpleStringProperty shutoffProperty() {
            return shutoff;
        }

        public void setShutoff(String shutoff) {
            this.shutoff.set(shutoff);
        }
    }
}
