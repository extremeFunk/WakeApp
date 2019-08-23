package io.rainrobot.wake.android.configuration;

import io.rainrobot.wake.android.view.AndroidEnterNewPasswordView;
import io.rainrobot.wake.android.view.AndroidEnterEmailView;
import io.rainrobot.wake.app.IViewFactory;
import io.rainrobot.wake.view.*;
import io.rainrobot.wake.android.textbox.AndroidTextBoxView;
import io.rainrobot.wake.android.view.deviceevents.AndroidDeviceEventsView;
import io.rainrobot.wake.android.view.devicereg.AndroidDeviceRegView;
import io.rainrobot.wake.android.view.eventlist.AndroidEventListView;
import io.rainrobot.wake.android.view.isnewdevice.IsNewDeviceImp;
import io.rainrobot.wake.android.view.login.AndroidLoginView;
import io.rainrobot.wake.android.view.mainmenu.AndroidMainMenuView;
import io.rainrobot.wake.android.view.presetlist.AndroidPresetView;
import io.rainrobot.wake.android.view.selectdevice.AndroidSelectDeviceView;
import io.rainrobot.wake.android.view.settings.AndroidSettingsView;
import io.rainrobot.wake.android.view.singup.AndroidSingupView;

public class AndroidViewFactory implements IViewFactory {

    private AndroidEnterEmailView enterEmail;
    private LoginView loginView;
    private TextBoxView textBoxView;
    private SingupView singupView;
    private SettingsView settingsView;
    private SelectDeviceView selectDevView;
    private PresetsView presetsView;
    private MainMenuView mainMenuView;

    private EventListView eventListView;
    private DeviceRegView deviceRegView;
    private DeviceEventsView deviceEventsView;
    private IsNewDeviceView isNewDeviceView;
    private EnterNewPasswordView enterNewPassord;

    public AndroidViewFactory(ContextMgr contextMgr) {
        textBoxView = new AndroidTextBoxView();
        loginView = new AndroidLoginView(contextMgr);
        singupView = new AndroidSingupView(contextMgr);
        settingsView = new AndroidSettingsView(contextMgr);
        selectDevView = new AndroidSelectDeviceView(contextMgr);
        presetsView = new AndroidPresetView(contextMgr);
        mainMenuView = new AndroidMainMenuView(contextMgr);
        eventListView = new AndroidEventListView(contextMgr);
        deviceRegView = new AndroidDeviceRegView(contextMgr);
        deviceEventsView = new AndroidDeviceEventsView(contextMgr);
        isNewDeviceView = new IsNewDeviceImp(contextMgr);
        enterNewPassord = new AndroidEnterNewPasswordView(contextMgr);
        enterEmail = new AndroidEnterEmailView(contextMgr);
    }

    @Override
    public LoginView getLoginView() {
        return this.loginView;
    }

    @Override
    public TextBoxView getTextBoxView() {
        return this.textBoxView;
    }

    @Override
    public SingupView getSingupView() {
        return this.singupView;
    }

    @Override
    public SettingsView getSettingsView() {
        return this.settingsView;
    }

    @Override
    public SelectDeviceView getSelectDeviceView() {
        return this.selectDevView;
    }

    @Override
    public PresetsView getPresetsView() {
        return this.presetsView;
    }

    @Override
    public MainMenuView getMainMenuView() {
        return this.mainMenuView;
    }

    @Override
    public EventListView getEventListView() {
        return this.eventListView;
    }

    @Override
    public DeviceRegView getDeviceRegView() {
        return this.deviceRegView;
    }

    @Override
    public DeviceEventsView getDeviceEventsView() {
        return this.deviceEventsView;
    }

    @Override
    public IsNewDeviceView getIsNewDeviceView() {
        return this.isNewDeviceView;
    }

    @Override
    public EnterNewPasswordView getEnterNewPasswordView() {
        return this.enterNewPassord;
    }

    @Override
    public EnterEmailView getEnterEmailView() {
        return enterEmail;
    }
}
