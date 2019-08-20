package io.rainrobot.wake.fx.view.dialog;

import io.rainrobot.wake.fx.Res;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class MsgDialog extends OkDialog {
    private Label label;

    public MsgDialog(MainStageMgr mainStageMgr) {
        super(mainStageMgr);
        scene.setFill(Color.TRANSPARENT);
        root.getStylesheets().add(Res.getCss("MsgDialog"));
    }

    public void show(String msg) {
        setStagePosition();
        label.setText(msg);
        super.show();
    }

    protected void setStagePosition() {
        double mainStageXPos = mainStageMgr.getXPos();
        double mainStageYPos = mainStageMgr.getYPost();
        double mainStageHeight = mainStageMgr.getHeight();
        double mainStageWidth = mainStageMgr.getWidth();

        double msgXPos = mainStageXPos + mainStageWidth/2 - stage.getWidth()/2;
        double msgYPos = mainStageYPos + mainStageHeight/3 - stage.getHeight()/2;

        stage.setX(msgXPos);
        stage.setY(msgYPos);
    }

    @Override
    protected Parent getContent() {
        label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(10));
        return label;
    }
}
