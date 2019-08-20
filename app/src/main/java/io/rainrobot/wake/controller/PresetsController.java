package io.rainrobot.wake.controller;

import java.util.Date;
import java.util.function.BiConsumer;

import io.rainrobot.wake.app.Controller;
import io.rainrobot.wake.app.ControllerMgr;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.util.IdCommand;
import io.rainrobot.wake.view.PresetsView;
import io.rainrobot.wake.model.PresetsModel;

public class PresetsController extends Controller<PresetsView, PresetsModel> {

    public PresetsController(PresetsView view,
                             PresetsModel model,
                             ControllerMgr controllerMgr,
                             ASyncProvider aSyncProvider) {

        super(view, model, controllerMgr, aSyncProvider);
    }

    public void injectDataToView() {
        asyncCall((
                () -> model.getPresets()),
                (presets) -> presets.forEach(p -> view.addRow(p)));
    }


    public void updateName(int id, String name) {
        asyncCall((() -> model.updateName(id, name)),
                (() -> view.updateName(id, name)));
    }


    public TimeCommand getTimeCommand() {
        return ((id, time) -> {
            asyncCall(() -> model.updateTime(id, time),
                    (() -> view.updateTime(id, time)));
        });
    }

    public ActiveStateCommand  getActiveStateCommand() {
        return ((id, state) -> {
            asyncCall(() -> model.setActiveState(id, state),
                        (() -> view.updateActiveState(id, state)));
        });
    }

    public NameCommand getRenameCommand() {
        return ((id, name) -> {
            asyncCall(() -> model.updateName(id, name),
                    (() -> view.updateName(id, name)));
        });

    }

    public IdCommand getEditCommand() {
        return ((id) -> {
            controllerMgr.showPresetEditor(id);
        });
    }

    public IdCommand getDeleteCommand() {
        return ((id) -> {
            asyncCall((() -> model.deletePreset(id)),
                    (() -> view.removeRow(id)));
        });
    }


    public Command getAddCommand() {
        return (() -> {
            asyncCall(  (() -> model.createPreset()),
                        ((preset)-> view.addRow(preset)));
        });
    }

    public Command getGoBackCommand() {
        return controllerMgr::showMainMenu;
    }

    public interface NameCommand {
        void execute(int id, String name);
    }
    public interface TimeCommand {
        void execute(int id, Date time);
    }
    public interface ActiveStateCommand {
        void execute(int id, boolean state);
    }
}



