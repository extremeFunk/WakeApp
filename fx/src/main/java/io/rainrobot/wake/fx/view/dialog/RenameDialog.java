package io.rainrobot.wake.fx.view.dialog;

import io.rainrobot.wake.fx.Res;
import io.rainrobot.wake.fx.view.component.FxmlLoader;
import io.rainrobot.wake.fx.view.component.StageDragHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class RenameDialog implements Initializable {

    @FXML
    TextField input;
    @FXML
    Button okBtn;
    @FXML
    Button cancelBtn;

    private final String name;
    private final Consumer<String> renameCmd;
    private Runnable cancelCmd;
    private final FxmlLoader loader = new FxmlLoader();
    private Stage stage;

    public RenameDialog(String currentName, Consumer<String> okCmd, Runnable cancelCmd) {
        this.name = currentName;
        this.renameCmd = okCmd;
        this.cancelCmd = cancelCmd;
    }

    public void show() {
        Parent parent = loader.load(Res.renameDialog(), this, "Main", "GrayDialog");
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);
        stage.getIcons().add(new Image(Res.icon_16()));
        stage.getIcons().add(new Image(Res.icon_32()));
        stage.getIcons().add(new Image(Res.icon_48()));
        new StageDragHandler().setMouseDrag(stage, parent);
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input.setText(name);
        okBtn.setOnAction((e) -> onOkClick());
        cancelBtn.setOnAction((e) -> {
            cancelCmd.run();
            close();
        });
    }

    public void onOkClick() {
        if(input.getText() != "") {
            renameCmd.accept(input.getText());
        }
        close();
    }

    private void close() {
        stage.close();
    }
}
