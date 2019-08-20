package io.rainrobot.wake.fx.view.dialog;

import io.rainrobot.wake.fx.Res;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.fx.view.component.StageDragHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class Dialog {

    protected Stage stage;
    protected Scene scene;
    protected VBox root;
    protected Parent content;
    protected MainStageMgr mainStageMgr;

    public Dialog(MainStageMgr mainStageMgr) {
        stage = new Stage();
        this.mainStageMgr = mainStageMgr;
        setUpWindow();
    }

    public void setUpWindow() {
        initializeParent();
        content = getContent();
        root.getChildren().add(content);
        scene = new Scene(root);
        new StageDragHandler().setMouseDrag(stage, root);
        stage.getIcons().addAll(Res.iconForStage());
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
    }

    public void initializeParent() {
        this.root = new VBox();
        this.root.getStylesheets().add(Res.getCss("Main"));
    }

    public void show() {
        mainStageMgr.disable();
        stage.show();
    }

    public void close() {
        mainStageMgr.enable();
        stage.close();
    }

    protected abstract Parent getContent();
}
