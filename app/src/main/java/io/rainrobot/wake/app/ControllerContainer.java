package io.rainrobot.wake.app;

import io.rainrobot.wake.controller.*;


public class ControllerContainer {

    private EnterEmailController enterEmail;

    public ControllerContainer() {

    }

    public ControllerContainer(ControllerFactory factory) {
        setDeviceEvent(factory.createDeviceEvents());
        setDeviceReg(factory.createDeviceRegister());
        setIsNewDevice(factory.createIsNewDevice());
        setLogin(factory.createLogin());
        setMainMenu(factory.createMainMenu());
        setPreset(factory.createPresets());
        setTextBox(factory.createTextBox());
        setSelectDevice(factory.createSelectDevice());
        setPresetEditor(factory.createEventList());
        setSettings(factory.createSettings());
        setSingup(factory.createSingup());
        setEnterNewPassword(factory.createEnterNewPassword());
        setEnterEmail(factory.createEnterEmail());
    }


    private LoginController login;

    private SingupController singup;

    private MainMenuController mainMenu;

    private PresetsController preset;

    private TextBoxController textBox;

    private EventListController presetEditor;

    private DeviceRegController deviceReg;

    private SettingsController settings;

    private DeviceEventsController deviceEvent;

    private SelectDeviceController selectDevice;

    private EnterNewPasswordController enterNewPassword;


    public LoginController getLogin() {
        return login;
    }

    public void setLogin(LoginController login) {
        this.login = login;
    }

    public SingupController getSingup() {
        return singup;
    }

    public void setSingup(SingupController singup) {
        this.singup = singup;
    }

    public MainMenuController getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(MainMenuController mainMenu) {
        this.mainMenu = mainMenu;
    }

    public PresetsController getPreset() {
        return preset;
    }

    public void setPreset(PresetsController preset) {
        this.preset = preset;
    }

    public TextBoxController getTextBox() {
        return textBox;
    }

    public void setTextBox(TextBoxController textBox) {
        this.textBox = textBox;
    }

    public EventListController getPresetEditor() {
        return presetEditor;
    }

    public void setPresetEditor(EventListController presetEditor) {
        this.presetEditor = presetEditor;
    }

    public DeviceRegController getDeviceReg() {
        return deviceReg;
    }

    public void setDeviceReg(DeviceRegController deviceReg) {
        this.deviceReg = deviceReg;
    }

    public SettingsController getSettings() {
        return settings;
    }

    public void setSettings(SettingsController settings) {
        this.settings = settings;
    }

    public DeviceEventsController getDeviceEvent() {
        return deviceEvent;
    }

    public void setDeviceEvent(DeviceEventsController deviceEvent) {
        this.deviceEvent = deviceEvent;
    }

    public SelectDeviceController getSelectDevice() {
        return selectDevice;
    }

    public void setSelectDevice(SelectDeviceController selectDevice) {
        this.selectDevice = selectDevice;
    }

    public IsNewDeviceController getIsNewDevice() {
        return isNewDevice;
    }

    public void setIsNewDevice(IsNewDeviceController isNewDevice) {
        this.isNewDevice = isNewDevice;
    }

    private IsNewDeviceController isNewDevice;

    public EnterNewPasswordController getEnterNewPassword() {
        return this.enterNewPassword;
    }

    public void setEnterNewPassword(EnterNewPasswordController enterNewPassword) {
        this.enterNewPassword = enterNewPassword;
    }

    public EnterEmailController getEnterEmail() {
        return this.enterEmail;
    }

    public void setEnterEmail(EnterEmailController enterEmail) {
        this.enterEmail = enterEmail;
    }
}
