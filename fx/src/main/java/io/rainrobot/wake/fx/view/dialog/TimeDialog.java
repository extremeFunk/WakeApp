package io.rainrobot.wake.fx.view.dialog;

import io.rainrobot.wake.fx.Res;
import io.rainrobot.wake.fx.view.component.FxmlLoader;
import io.rainrobot.wake.core.util.DateUtil;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.fx.view.component.StageDragHandler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class TimeDialog implements Initializable {
    private final Date currentTime;
    private final Consumer<Date> okCmd;
    private final Runnable cancelCmd;

    @FXML
    Button okBtn;
    @FXML
    Button cancelBtn;

    @FXML
    Button plus5Hr;
    @FXML
    Button minus5Hr;
    @FXML
    Spinner<Integer> hrSpinner;

    @FXML
    Button plus5Mn;
    @FXML
    Button minus5Mn;
    @FXML
    Spinner<Integer> mnSpinner;

    private Stage stage;

    public TimeDialog(Date currentTime, Consumer<Date> onOkCmd, Runnable cancelCmd) {
        this.currentTime = currentTime;
        this.cancelCmd = cancelCmd;
        this.okCmd = onOkCmd;
        stage = new Stage();
    }

    public void show() {
        Parent parent = new FxmlLoader().load(getClass().getResource("/fxml/dialog/time_dialog.fxml"),
                                                                    this);
        new StageDragHandler().setMouseDrag(stage, parent);
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);
        stage.getIcons().addAll(new Image(Res.icon_16()),
                                new Image(Res.icon_32()),
                                new Image(Res.icon_48()));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //minuts
        mnSpinner.getStyleClass().add("split-arrows-vertical");
        int mn = DateUtil.getMn(currentTime);
        SpinnerValueFactory<Integer> mnFact
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, mn);
        mnFact.wrapAroundProperty().set(true);
        mnFact.setConverter(getStringConverter());
        mnSpinner.setValueFactory(mnFact);

        plus5Mn.setOnAction(e -> mnFact.setValue((mnFact.getValue() + 5) % 59));
        minus5Mn.setOnAction(e -> mnFact.setValue((mnFact.getValue() - 5) % 59));

        //hours
        hrSpinner.getStyleClass().add("split-arrows-vertical");
        int hr = DateUtil.getHr(currentTime);
        SpinnerValueFactory<Integer> hrFact
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, hr);
        hrFact.wrapAroundProperty().set(true);
        hrFact.setConverter(getStringConverter());
        hrSpinner.setValueFactory(hrFact);

        plus5Hr.setOnAction(e -> hrFact.setValue((hrSpinner.getValue() + 5) % 23));
        minus5Hr.setOnAction(e -> hrFact.setValue((hrSpinner.getValue() - 5) % 23));

        //startApp
        okBtn.setOnAction(e -> { onOkClick(); stage.close(); });
        cancelBtn.setOnAction(e -> {
            stage.close();
            cancelCmd.run();
        });

    }

    private void onOkClick() {
        Date nuTime
            = DateUtil.fromHrAndMn(hrSpinner.getValue(), mnSpinner.getValue());
        okCmd.accept(nuTime);
    }

    public StringConverter<Integer> getStringConverter() {
        return new StringConverter<Integer>() {
            @Override
            public String toString(Integer i) {
                String str = i.toString();
                if (str.length() == 1) str = "0" + str;
                return str;
            }

            @Override
            public Integer fromString(String str) {
                if (str.length() == 2 && str.charAt(1) == 0) {
                    str.subSequence(1, 1);
                }
                return Integer.valueOf(str);
            }
        };
    }
}
