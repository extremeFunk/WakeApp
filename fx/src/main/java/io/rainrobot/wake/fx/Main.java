package io.rainrobot.wake.fx;

import io.rainrobot.wake.core.util.Log;
import io.rainrobot.wake.fx.config.FxConfiguration;
import io.rainrobot.wake.fx.view.component.FxmlLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    public static boolean isAppShowing = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        showSplashScreen(stage);
        Platform.runLater(() -> startApp(stage));
    }

    private void showSplashScreen(Stage stage) {
        stage.setResizable(false);
        stage.setTitle("WakeApp");
        stage.getIcons().addAll(Res.iconForStage());
        Parent parent = new FxmlLoader().load(Res.splash(),
                null,"PrimaryPortlet", "Main");
        stage.setScene(new Scene(parent));
        stage.show();
    }


    public void startApp(Stage stage) {
        FxConfiguration config = new FxConfiguration(stage);
        ConfigContainer.setConfig(config);
        setTrayIcon();
        config.getApp().start();
        isAppShowing = true;
    }


    private void setTrayIcon() {
        // ensure awt toolkit is initialized.
        Toolkit.getDefaultToolkit();

        // app requires system tray support, just exit if there is no support.
        if (!SystemTray.isSupported()) {
            Log.d(this, "No system tray support, application exiting.");
            Platform.exit();
        }

        Platform.setImplicitExit(false);

        // set up a system tray icon_32.
        SystemTray tray = SystemTray.getSystemTray();
        URL url = getClass().getResource("/icon_16.png");
        Image image = null;
        try {
            image = ImageIO.read(url);
        }
        catch (IOException e) {
            Log.err(this, e);
        }
        TrayIcon trayIcon = new TrayIcon(image);
        trayIcon.setImageAutoSize(false);

        // if the user double-clicks on the tray icon_32, show the startApp app stage.
        trayIcon.addActionListener(event -> trayShowCmd());

        // if the user selects the default menu item (which includes the app name),
        // show the startApp app stage.
        MenuItem openItem = new MenuItem("Open");
        openItem.addActionListener(event -> trayShowCmd());

        // the convention for tray icons seems to be to set the default icon_32 for opening
        // the application stage in a bold font.
//        Font defaultFont = Font.decode(null);
//        Font boldFont = defaultFont.deriveFont(Font.BOLD);
//        openItem.setFont(boldFont);

        // to really exit the application, the user must go to the system tray icon_32
        // and select the exit option, this will shutdown JavaFX and remove the
        // tray icon_32 (removing the tray icon_32 will also shut down AWT).
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(event -> {
            Platform.exit();
            tray.remove(trayIcon);
            System.exit(0);
        });

        // setup the popup menu for the application.
        final PopupMenu popup = new PopupMenu();
        popup.add(openItem);
        popup.addSeparator();
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try { tray.add(trayIcon); }
        catch (AWTException e) { Log.err(this, e); }

    }

    private void trayShowCmd() {
        Platform.runLater(() -> {
            if(isAppShowing) {  }
            else {
                ConfigContainer.getConfig().getApp().start();
                isAppShowing = true;
            }
        });
    }
}
