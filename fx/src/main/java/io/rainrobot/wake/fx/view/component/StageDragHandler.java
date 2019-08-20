package io.rainrobot.wake.fx.view.component;

import javafx.scene.Parent;
import javafx.stage.Stage;


public class StageDragHandler {

    private double dragYOffset;
    private double dragXOffset;

    public void setMouseDrag(Stage stage, Parent parent) {
        parent.setOnMousePressed((event) -> {
            dragXOffset = stage.getX() - event.getScreenX();
            dragYOffset = stage.getY() - event.getScreenY();
        });

        parent.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + dragXOffset);
            stage.setY(event.getScreenY() + dragYOffset);
        });
    }
}
