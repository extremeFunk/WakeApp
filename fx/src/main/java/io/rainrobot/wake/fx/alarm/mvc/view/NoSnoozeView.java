package io.rainrobot.wake.fx.alarm.mvc.view;

import io.rainrobot.wake.fx.view.component.FxmlLoader;
import javafx.stage.Stage;

import java.net.URL;

public class NoSnoozeView extends AlarmView{

    public NoSnoozeView(FxmlLoader fxmlLoader, Stage stage) {
        super(fxmlLoader, stage);
    }

    @Override
    public URL getUrl() {
        return getClass().getResource("/fxml/view/no_snooze_alarm.fxml");
    }
}


