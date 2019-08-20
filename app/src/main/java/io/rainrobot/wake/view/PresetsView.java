package io.rainrobot.wake.view;

import java.util.Date;

import io.rainrobot.wake.app.IView;
import io.rainrobot.wake.controller.PresetsController;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.util.IdCommand;

public interface PresetsView extends IView<PresetsController> {


	void addRow(Preset preset);
	void removeRow(int id);

	void updateName(int id, String name);
	void updateTime(int id, Date time);
	void updateActiveState(int id, boolean state);
}
