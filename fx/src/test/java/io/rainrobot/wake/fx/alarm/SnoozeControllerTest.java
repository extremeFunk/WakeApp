package io.rainrobot.wake.fx.alarm;

import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.core.Sound;
import io.rainrobot.wake.fx.JavaFXThreadingRule;
import io.rainrobot.wake.fx.config.FxConfiguration;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

public class SnoozeControllerTest  {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void test() throws Exception {
        FxConfiguration config = new FxConfiguration(new Stage());
        config.getAlarmControllerMgr().showWithSnooze(getEvent());
    }


    private AlarmEvent getEvent() {
        return new AlarmEvent.Builder()
                .setVol(70)
                .setSound(Sound.BELL)
                .setSnooze(1)
                .setShutOff(3)
                .setDelay(0)
                .setId(1)
                .setPreset(getPreset())
                .build();
    }

    private Preset getPreset() {
        Preset preset = new Preset();
        preset.setTime(new Date());
        return preset;
    }
}