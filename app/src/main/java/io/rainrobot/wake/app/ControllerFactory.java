package io.rainrobot.wake.app;

import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.controller.*;
import io.rainrobot.wake.model.*;
import io.rainrobot.wake.view.*;

public class ControllerFactory {

    private ControllerMgr controllerMgr;
    private IViewFactory viewFactory;
    private ModelFactory modelFactory;
    private ASyncProvider aSyncProvider;
    private AppLogger logger;



    public ControllerFactory(IViewFactory viewFactory,
                             ModelFactory modelFactory,
                             ASyncProvider aSyncProvider, AppLogger logger) {
        this.viewFactory = viewFactory;
        this.modelFactory = modelFactory;
        this.aSyncProvider = aSyncProvider;
        this.logger = logger;
    }


    
    public LoginController createLogin() {
        LoginView view = viewFactory.getLoginView();
        LoginModel model = modelFactory.getLogin();
        return new LoginController(view, model,logger, controllerMgr, aSyncProvider);
    }

    
    public MainMenuController createMainMenu() {
        MainMenuView view = viewFactory.getMainMenuView();
        MainMenuModel model = modelFactory.getMainMenu();
        return new MainMenuController(view, model, controllerMgr, aSyncProvider, logger);
    }

    
    public SingupController createSingup() {
        SingupView view = viewFactory.getSingupView();
        SighupModel model = modelFactory.getSignup();
        return new SingupController(view, model, controllerMgr, aSyncProvider);
    }

    
    public PresetsController createPresets() {
        PresetsView view = viewFactory.getPresetsView();
        PresetsModel model = modelFactory.getPresets();
        return new PresetsController(view, model, controllerMgr, aSyncProvider);
    }

    
    public SettingsController createSettings() {
        SettingsView view = viewFactory.getSettingsView();
        SettingsModel model = modelFactory.getSettings();
        return new SettingsController(view, model, controllerMgr, aSyncProvider);
    }

    
    public DeviceEventsController createDeviceEvents() {
        DeviceEventsView view = viewFactory.getDeviceEventsView();
        DeviceEventsModel model = modelFactory.getDeviceEvents();
        return new DeviceEventsController(view, model, controllerMgr, aSyncProvider);
    }

    
    public EventListController createEventList() {
        EventListView view = viewFactory.getEventListView();
        EventListModel model = modelFactory.getEventList();
        return new EventListController(view, model, controllerMgr, aSyncProvider);
    }

    
    public DeviceRegController createDeviceRegister() {
        DeviceRegView view = viewFactory.getDeviceRegView();
        DeviceRegModel model = modelFactory.getDeviceReg();
        return new DeviceRegController(view, model, controllerMgr, aSyncProvider, logger);
    }

    
    public SelectDeviceController createSelectDevice() {
        SelectDeviceView view = viewFactory.getSelectDeviceView();
        SelectDeviceModel model = modelFactory.getSelectDevice();
        return new SelectDeviceController(view, model, controllerMgr, aSyncProvider, logger);
    }

    
    public IsNewDeviceController createIsNewDevice() {
        IsNewDeviceView view = viewFactory.getIsNewDeviceView();
        IsNewDeviceModel model = modelFactory.getIsNewDevice();
        return new IsNewDeviceController(view, model, controllerMgr, aSyncProvider);
    }

    public void setControllerMgr(ControllerMgr controllerMgr) {
        this.controllerMgr = controllerMgr;
    }

    
    public TextBoxController createTextBox() {
        TextBoxView view = viewFactory.getTextBoxView();
        return new TextBoxController(view);
    }

    public EnterNewPasswordController createEnterNewPassword() {
        EnterNewPasswordView view = viewFactory.getEnterNewPasswordView();
        EnterNewPasswordModel model = modelFactory.getEnterNewPassword();
        return new EnterNewPasswordController(view, model, controllerMgr, aSyncProvider);
    }

    public EnterEmailController createEnterEmail() {
        EnterEmailView view = viewFactory.getEnterEmailView();
        EnterEmailModel model = modelFactory.getEnterEmailModel();
        return new EnterEmailController(view, model, controllerMgr, aSyncProvider);
    }
}

