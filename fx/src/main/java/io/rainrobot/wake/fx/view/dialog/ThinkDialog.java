package io.rainrobot.wake.fx.view.dialog;

import io.rainrobot.wake.fx.Res;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class ThinkDialog extends FxmlDialog{

    private static final int thinkModeMinimum = 1;
    private int thinkMode = thinkModeMinimum - 2;

    public ThinkDialog(MainStageMgr mainStageMgr) {
        super(mainStageMgr);
        root.getStylesheets().add(Res.getCss("ThinkDialog"));
        super.scene.setFill(Color.TRANSPARENT);
    }

    @Override
    protected URL getUrl() {
        return Res.getView("splash_screen");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @Override
    public void show() {
        //the below diagram represent a multiple request logic
        //-1 0 <- show-A 1 <- show-B  close-A -> 0 close-B -> -1 0 <- show-C
        thinkMode++;
        if(thinkMode < thinkModeMinimum) {
            super.show();
        }
    }

    @Override
    public void close() {
        if(thinkMode < thinkModeMinimum) {
            super.close();
        }
        thinkMode--;
    }
}
