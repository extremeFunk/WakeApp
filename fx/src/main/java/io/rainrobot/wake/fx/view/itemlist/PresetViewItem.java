package io.rainrobot.wake.fx.view.itemlist;

import io.rainrobot.wake.controller.PresetsController;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.fx.view.dialog.RenameDialog;
import io.rainrobot.wake.fx.view.dialog.TimeDialog;
import io.rainrobot.wake.core.util.DateUtil;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.util.IdCommand;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class PresetViewItem extends FxmlItem {

    private final Preset preset;

    private final IdCommand deleteCommand;
    private final IdCommand editCommand;
    private final MainStageMgr mainStageMgr;
    private final PresetsController.NameCommand renameCommand;
    private final PresetsController.TimeCommand timeCommand;
    private final PresetsController.ActiveStateCommand activeStateCommand;

    @FXML
    Button nameLabel;
    @FXML
    Button timeLabel;
    @FXML
    ToggleButton activeToggle;
    @FXML
    Button editBtn;
    @FXML
    Button deleteBtn;

    private PresetViewItem(Builder b) {
        preset = b.preset;
        deleteCommand = b.deleteCommand;
        renameCommand = b.renameCommand;
        timeCommand = b.timeCommand;
        activeStateCommand = b.activeStateCommand;
        editCommand = b.editCommand;
        mainStageMgr = b.mainStageMgr;
    }

    @Override
    protected URL getUrl() {
        return getClass().getResource("/fxml/item/preset_item.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameLabel.setText(preset.getName());
        nameLabel.setOnMouseClicked((e) -> { showRenameDialog(); });

        timeLabel.setText(DateUtil.toHrMnTxt(preset.getTime()));
        timeLabel.setOnMouseClicked((e) -> { showTimeDiaolg(); });

        activeToggle.setSelected(preset.isActiveState());
        activeToggle.selectedProperty().addListener(
                (obsrv, old, nu) -> activeStateCommand.execute(preset.getId(), nu.booleanValue()));

        deleteBtn.setOnAction((e) -> deleteCommand.execute(preset.getId()));

        editBtn.setOnAction((e) -> editCommand.execute(preset.getId()));
    }

    private void showTimeDiaolg() {
        Consumer<Date> okCmd = (time) -> {
            mainStageMgr.disable();
            timeCommand.execute(preset.getId(), time);
        };
        TimeDialog timeDialog = new TimeDialog(preset.getTime(), okCmd,
                                                mainStageMgr::enable);
        mainStageMgr.disable();
        timeDialog.show();
    }

    private void showRenameDialog() {
        Consumer<String> okCmd = (name) -> {
            renameCommand.execute(preset.getId(), name);
            mainStageMgr.enable();
        };
        RenameDialog renameDialog = new RenameDialog(preset.getName(),
                                                        okCmd, mainStageMgr::enable);
        mainStageMgr.disable();
        renameDialog.show();
    }

    @Override
    public Integer getId() {
        return preset.getId();
    }

    public void setTime(Date time) {
        timeLabel.setText(DateUtil.toHrMnTxt(time));
        preset.setTime(time);
    }

    public void setActiveSate(boolean state) {
        activeToggle.setSelected(state);
        preset.setActiveState(state);
    }

    public void setName(String name) {
        nameLabel.setText(name);
        preset.setName(name);
    }

    public static class Builder {

        public MainStageMgr mainStageMgr;

        private Preset preset;

        private IdCommand deleteCommand;
        private PresetsController.NameCommand renameCommand;
        private PresetsController.TimeCommand timeCommand;
        private PresetsController.ActiveStateCommand activeStateCommand;
        private IdCommand editCommand;

        public PresetViewItem build() {
            return new PresetViewItem(this);
        }

        public Builder setPreset(Preset preset) {
            this.preset = preset;
            return this;
        }

        public Builder setMainStageMgr(MainStageMgr mainStageMgr) {
            this.mainStageMgr = mainStageMgr;
            return this;
        }

        public Builder setDeleteCmd(IdCommand deleteCommand) {
            this.deleteCommand = deleteCommand;
            return this;
        }

        public Builder setRenameCmd(PresetsController.NameCommand renameCommand) {
            this.renameCommand = renameCommand;
            return this;
        }

        public Builder setTimeCmd(PresetsController.TimeCommand timeCommand) {
            this.timeCommand = timeCommand;
            return this;
        }

        public Builder setActiveCmd(PresetsController.ActiveStateCommand activeStateCommand) {
            this.activeStateCommand = activeStateCommand;
            return this;
        }

        public Builder setEditCmd(IdCommand editCommand) {
            this.editCommand = editCommand;
            return this;
        }
    }

}
