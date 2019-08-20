package io.rainrobot.wake.fx.alarm.mvc.view;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.fx.alarm.mvc.SnoozeController;
import io.rainrobot.wake.fx.view.component.FxmlLoader;
import io.rainrobot.wake.core.util.DateUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public abstract class AlarmView implements Initializable {
    @FXML
    private Button turnoffBtn;

    protected  FxmlLoader fxmlLoader;
    private MediaPlayer player;
    private SnoozeController ctrl;

    private AlarmEvent event;
    private Stage stage;

    public AlarmView(FxmlLoader fxmlLoader, Stage stage) {
        this.fxmlLoader = fxmlLoader;
        this.stage = stage;
    }

    public void setCtrl(SnoozeController ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        turnoffBtn.setOnAction((e) -> close());
//        if(!stage.isShowing())
    }

    public void show(AlarmEvent event) {
        this.event = event;
        if (stage.getScene() == null) {
            setStage();
        }
        stage.show();
        play();
    }

    public void setStage() {
        Parent parent = fxmlLoader.load(getUrl(), this, "Main", "GrayDialog");
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
    }

    public abstract URL getUrl();

    public void close() {
        player.stop();
        stage.close();
    }

    public void play() {
        String source = getSoundRes();
        Media pick = new Media(source);
        player = new MediaPlayer(pick);
        player.setVolume(event.getVol());
        player.setOnEndOfMedia(() -> {
            if (notShutoff()) player.play();
            else { if (notAfterShutoff()) player.play(); }
        });
        player.play();
    }

    public String getSoundRes() {
        return getClass()
                .getResource("/mp3/" + event.getSound().name() + ".mp3")
                .toString();
    }

    public boolean notShutoff() {
        return event.getShutOff() == AlarmEvent.SHUTOFF_OFF;
    }

    public boolean notAfterShutoff() {
        return new Date().before(DateUtil.plusMinuets(event.getTime(), event.getShutOff()));
    }
}
