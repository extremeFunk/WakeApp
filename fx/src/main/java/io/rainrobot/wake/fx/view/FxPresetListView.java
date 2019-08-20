package io.rainrobot.wake.fx.view;

import io.rainrobot.wake.controller.PresetsController;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.fx.Res;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.fx.view.component.FxView;
import io.rainrobot.wake.fx.view.itemlist.ItemList;
import io.rainrobot.wake.fx.view.itemlist.PresetViewItem;
import io.rainrobot.wake.view.PresetsView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class FxPresetListView extends FxView<PresetsController> implements PresetsView {
    @FXML
    Button goBackBtn;
    @FXML
    Button addBtn;
    @FXML
    ScrollPane scrollPane;
    @FXML
    ItemList<PresetViewItem> itemBox;

    private PresetsController ctrl;

    public FxPresetListView(MainStageMgr loader) {
        super(loader);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.getStylesheets().add(Res.getCss("Main"));
        goBackBtn.setOnAction((e) -> ctrl.getGoBackCommand().execute());
        ctrl.injectDataToView();
        addBtn.setOnAction((e) -> ctrl.getAddCommand().execute());
    }

    @Override
    protected String getFxmlName() {
        return "presets";
    }

    @Override
    public void setController(PresetsController controller) {
        this.ctrl = controller;
    }

    @Override
    public void addRow(Preset preset) {
        itemBox.add(createItem(preset));
    }

    private PresetViewItem createItem(Preset preset) {
        PresetsController.ActiveStateCommand activeCmd;
        return new PresetViewItem.Builder()
                                .setPreset(preset)
                                .setDeleteCmd(ctrl.getDeleteCommand())
                                .setRenameCmd(ctrl.getRenameCommand())
                                .setTimeCmd(ctrl.getTimeCommand())
                                .setActiveCmd(ctrl.getActiveStateCommand())
                                .setEditCmd(ctrl.getEditCommand())
                                .setMainStageMgr(mainWindowMgr)
                                .build();
    }

    @Override
    public void removeRow(int id) {
        itemBox.remove(id);
    }

    @Override
    public void updateName(int id, String name) {
        itemBox.getItem(id).setName(name);
    }

    @Override
    public void updateTime(int id, Date time) {
        itemBox.getItem(id).setTime(time);
    }

    @Override
    public void updateActiveState(int id, boolean state) {
        itemBox.getItem(id).setActiveSate(state);
    }
}
