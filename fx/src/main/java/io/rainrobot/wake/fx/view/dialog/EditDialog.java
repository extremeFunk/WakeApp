package io.rainrobot.wake.fx.view.dialog;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.Sound;
import io.rainrobot.wake.core.util.EventUtil;
import io.rainrobot.wake.fx.Res;
import io.rainrobot.wake.fx.view.component.FxmlLoader;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.fx.view.component.StageDragHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

public class EditDialog implements Initializable {
    private final MainStageMgr mainWindowMgr;
    @FXML
    Button okBtn;
    @FXML
    Button cancelBtn;
    @FXML
    ComboBox<String> deviceCBox;
    @FXML
    ComboBox<String> soundCBox;
    @FXML
    Slider volSlider;
    @FXML
    Spinner<Integer> delaySpinner;
    @FXML
    Spinner<Integer> snoozeSpinner;
    @FXML
    Spinner<Integer> shutoffSpinner;
    @FXML
    Toggle snoozeToggle;
    @FXML
    Toggle shutoffToggle;

    private final AlarmEvent bufferEvent;
    private final Consumer<AlarmEvent> updateCommand;
    private final Map<String, Device> deviceMap = new HashMap<>();
    private Stage stage;


    public EditDialog(AlarmEvent event,
                      Consumer<AlarmEvent> updateCommand,
                      Map<Integer, Device> idDeviceMap,
                      MainStageMgr mainWindowMgr) {
        this.mainWindowMgr = mainWindowMgr;
        this.stage = new Stage();
        this.bufferEvent = event.clone();
        this.updateCommand = updateCommand;
        bufferEvent.setDevice(getDeviceFromMap(idDeviceMap));
        convertDeviceMap(idDeviceMap);
    }

    public Device getDeviceFromMap(Map<Integer, Device> idDeviceMap) {
        return idDeviceMap.get(bufferEvent.getDevice().getId());
    }

    private void convertDeviceMap(Map<Integer, Device> devices) {
        devices.forEach((id, dev) -> deviceMap.put(dev.getName(), dev));
    }

    public void show() {
        mainWindowMgr.disable();
        URL url = getClass().getResource("/fxml/dialog/edit_dialog.fxml");
        Parent parent
                = new FxmlLoader().load(url, this, "Main", "GrayDialog");
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.getIcons().add(new Image(Res.icon_16()));
        stage.getIcons().add(new Image(Res.icon_32()));
        stage.getIcons().add(new Image(Res.icon_48()));
        new StageDragHandler().setMouseDrag(stage, parent);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setComponentsOnAction();
        //value need to be set after for the toggle action to take
        //effect at initializing
        setComponentsValue();
    }

    public void setComponentsValue() {
        //device
        deviceMap.forEach((nm, dev) -> deviceCBox.getItems().add(nm));
        deviceCBox.setValue(EventUtil.deviceText(bufferEvent));
        //sound
        for (Sound s : Sound.values()) { soundCBox.getItems().add(s.name()); }
        soundCBox.setValue(EventUtil.soundText(bufferEvent));
        //delay
        initilizeSpinner(delaySpinner,
                AlarmEvent.DELAY_MIN,
                AlarmEvent.DELAY_MAX,
                AlarmEvent.DELAY_SUFFIX,
                1,
                bufferEvent.getDelay());
        //snooze
        initilizeSpinner(snoozeSpinner,
                AlarmEvent.SNOOZE_MIN,
                AlarmEvent.SNOOZE_MAX,
                AlarmEvent.SNOOZE_SUFFIX,
                1,
                bufferEvent.getSnooze());
        boolean isSnooze = EventUtil.isSnooze(bufferEvent);
        snoozeSpinner.setDisable(!isSnooze);
        snoozeToggle.setSelected(isSnooze);
        //shutoff
        initilizeSpinner(shutoffSpinner,
                AlarmEvent.SHUTOFF_MIN,
                AlarmEvent.SHUTOFF_MAX,
                AlarmEvent.SHUTOFF_SUFFIX,
                AlarmEvent.SHUTOFF_INTERVAL,
                bufferEvent.getShutOff());
        boolean isShutoff = EventUtil.isShutoff(bufferEvent);
        shutoffSpinner.setDisable(!isShutoff);
        shutoffToggle.setSelected(isShutoff);
        //vol
        //this value need to be protected from setMin/Max methods
        final int vol = bufferEvent.getVol();
        volSlider.setMin(AlarmEvent.VOL_MIN);
        volSlider.setMax(AlarmEvent.VOL_MAX);
        volSlider.setValue(vol);
    }

    private void initilizeSpinner(Spinner<Integer> spinner,
                                  int min,
                                  int max,
                                  final String suffix,
                                  int interval,
                                  int initValue) {

        SpinnerValueFactory<Integer> fact =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initValue, interval);

        fact.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer val) {
                return val + suffix;
            }

            @Override
            public Integer fromString(String string) {
                return Integer.valueOf(string.replace(suffix, ""));
            }
        });
        spinner.setValueFactory(fact);
    }

    public void setComponentsOnAction() {
        //ok
        okBtn.setOnAction(e -> { updateCommand.accept(bufferEvent); close(); });
        //cancel
        cancelBtn.setOnAction(e -> close());
        //delay
        delaySpinner.valueProperty().addListener((obs, old, nu) -> { bufferEvent.setDelay(nu); });
        //snooze
        snoozeSpinner.valueProperty().addListener((obs, old, nu) -> { bufferEvent.setSnooze(nu); });
        snoozeToggle.selectedProperty().addListener(
                (obs, old, nu) -> {
                    snoozeSpinner.setDisable(!nu);
                    if (nu) { bufferEvent.setSnooze(snoozeSpinner.getValue()); }
                    else { bufferEvent.setSnooze(AlarmEvent.SNOOZE_OFF); }
                }
        );
        //shutoff
        shutoffSpinner.valueProperty().addListener((obs, old, nu) -> { bufferEvent.setShutOff(nu); });
        shutoffToggle.selectedProperty().addListener(
                (obs, old, nu) -> {
                    shutoffSpinner.setDisable(!nu);
                    if (nu) { bufferEvent.setShutOff(shutoffSpinner.getValue()); }
                    else { bufferEvent.setShutOff(AlarmEvent.SHUTOFF_OFF); }
                }
        );

        //device
        deviceCBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, old, nu) -> bufferEvent.setDevice(deviceMap.get(nu)));
        //sound
        soundCBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, old, nu) -> bufferEvent.setSound(Sound.valueOf(nu)));
        //vol
        volSlider.valueProperty().addListener(
                (obs, old, nu) -> bufferEvent.setVol(nu.intValue())
        );
    }

    private void close() {
        mainWindowMgr.enable();
        stage.close();
    }

}
