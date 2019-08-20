package io.rainrobot.wake.app;

import io.rainrobot.wake.util.Command;

public class ControllerMgr {

	private ControllerMgr() {

	}

	private ControllerContainer container;

	public void setContainer(ControllerContainer container) {
		this.container = container;
	}


	public void showLogin() {
		container.getLogin().show();
	}


	public void showMainMenu() { container.getMainMenu().show();
	}

	public void showSingup() {
		container.getSingup().show();
	}

	public void showPresets() {
		container.getPreset().show();
	}


	public void showSettings() {
		container.getSettings().show();
	}


	public void showDeviceEvents() {
		container.getDeviceEvent().show();
	}


	public void showPresetEditor(int id) {
		container.getPresetEditor().show(id);
	}


	public void showDeviceRegister() {
		container.getDeviceReg().show();
	}


	public void showSelectDevice() {
		container.getSelectDevice().show();
	}

	
	public void showIsNewDevice() {
		container.getIsNewDevice().show();
	}


	public void showEnterNewPassword() {
		container.getEnterNewPassword().show();
	}

	public void showEnterMail() { container.getEnterEmail().show(); }

	public void showPresetRenameBox(final int id) {

		container.getTextBox().show("Rename", "Enter new name:");
		container.getTextBox().setOnOkCommand(new Command() {

			public void execute() {
				textBoxOnOk(id);
			}
		});
	}

	private void textBoxOnOk(int id) {

		if (container.getTextBox().getInput() == "") {
			return;
		}

		else {
			container.getPreset().updateName(id, container.getTextBox().getInput());
			container.getTextBox().close();
		}
	}

	public static ControllerMgr create(ControllerFactory factory, AppLogger logger) {
		ControllerMgr mgr = new ControllerMgr();
		factory.setControllerMgr(mgr);
		logger.setControllerMgr(mgr);
		ControllerContainer container = new ControllerContainer(factory);
		mgr.setContainer(container);
		return mgr;
	}
}
