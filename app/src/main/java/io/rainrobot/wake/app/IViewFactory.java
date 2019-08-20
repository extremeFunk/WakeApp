package io.rainrobot.wake.app;

import io.rainrobot.wake.view.*;

public interface IViewFactory {

    LoginView getLoginView();

    TextBoxView getTextBoxView();

    SingupView getSingupView();

    SettingsView getSettingsView();

    SelectDeviceView getSelectDeviceView();

    PresetsView getPresetsView();

    MainMenuView getMainMenuView();

    EventListView getEventListView();

    DeviceRegView getDeviceRegView();

    DeviceEventsView getDeviceEventsView();

    IsNewDeviceView getIsNewDeviceView();

    EnterNewPasswordView getEnterNewPasswordView();

    EnterEmailView getEnterEmailView();
}
