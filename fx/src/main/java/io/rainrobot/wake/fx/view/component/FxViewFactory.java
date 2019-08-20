package io.rainrobot.wake.fx.view.component;

import io.rainrobot.wake.app.IViewFactory;
import io.rainrobot.wake.fx.view.*;
import io.rainrobot.wake.fx.view.FxEventListView;
import io.rainrobot.wake.view.*;

public class FxViewFactory implements IViewFactory {

	private MainStageMgr loader;

	public FxViewFactory(MainStageMgr loader) {
		this.loader = loader;
	}

	@Override
	public LoginView getLoginView() {
		return new FxLoginView(loader);
	}

	@Override
	public TextBoxView getTextBoxView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingupView getSingupView() {
		return new FxSingupView(loader);
	}

	@Override
	public SettingsView getSettingsView() {
		return new FxSettingsView(loader);
	}

	@Override
	public SelectDeviceView getSelectDeviceView() {
		return new FxSelectDeviceView(loader);
	}

	@Override
	public PresetsView getPresetsView() {
		return new FxPresetListView(loader);
	}

	@Override
	public MainMenuView getMainMenuView() {
		return new FxMainMenuView(loader);
	}

	@Override
	public EventListView getEventListView() {
		return new FxEventListView(loader);
	}

	@Override
	public DeviceRegView getDeviceRegView() {
		return new FxDeviceRegView(loader);
	}

	@Override
	public DeviceEventsView getDeviceEventsView() {
		return new FxDeviceEventView(loader);
	}

	@Override
	public IsNewDeviceView getIsNewDeviceView() {
		return new FxIsNewDeviceView(loader);
	}

	@Override
	public EnterNewPasswordView getEnterNewPasswordView() {
		return new FxEnterNewPassword(loader);
	}

	@Override
	public EnterEmailView getEnterEmailView() {
		return new FxEnterMailView(loader);
	}

}
