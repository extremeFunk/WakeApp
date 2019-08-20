package io.rainrobot.wake.fx.alarm.mvc.view;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.fx.alarm.mvc.SnoozeController;
import io.rainrobot.wake.fx.view.component.FxmlLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SnoozeView extends AlarmView{

    @FXML
    private Button snoozeBtn;

    private SnoozeController ctrl;

    private AlarmEvent event;


    public SnoozeView(FxmlLoader fxmlLoader, Stage stage) {
        super(fxmlLoader, stage);
    }

    public void setCtrl(SnoozeController ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        snoozeBtn.setOnAction((e) -> ctrl.getSnoozeCmd().run());
    }


    public URL getUrl() {
        return getClass().getResource("/fxml/view/snooze_alarm.fxml");
    }

}
