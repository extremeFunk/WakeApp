package io.rainrobot.wake.fx.view;

import io.rainrobot.wake.controller.SelectDeviceController;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.fx.view.component.FxView;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.view.SelectDeviceView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FxSelectDeviceView extends FxView<SelectDeviceController> implements SelectDeviceView {

    private SelectDeviceController ctrl;
    @FXML
    Button selectBtn;
    @FXML
    Button goBackBtn;
    @FXML
    VBox deviceBox;

    private Device selectedDevice;
    private ToggleGroup tGrp;

    public FxSelectDeviceView(MainStageMgr loader) {
        super(loader);
    }

    @Override
    protected String getFxmlName() {
        return "select_device";
    }

    @Override
    public void setController(SelectDeviceController controller) {
        this.ctrl = controller;
    }


    @Override
    public void addDev(List<Device> deviceList) {
        deviceList.forEach(dev -> {
            deviceBox.getChildren().add(createItem(dev));
        });
    }

    private Node createItem(Device dev) {
        RadioButton rb = new RadioButton();
        rb.setToggleGroup(tGrp);
        rb.setText(dev.getName());
        rb.setOnAction(e -> {
            selectedDevice = dev;
        });
        return rb;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tGrp = new ToggleGroup();
        ctrl.addDevices();
        selectBtn.setOnAction((e) -> onSelectedClick());
        goBackBtn.setOnAction((e) -> ctrl.getGoBackCommand().execute());
    }

    public void onSelectedClick() {
        if(selectedDevice == null) super.showMsg("No device was selected");
        else ctrl.getSelectCommand().accept(selectedDevice);
    }
}
