package io.rainrobot.wake.fx.view.component;

import io.rainrobot.wake.core.util.Log;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class FxmlLoader {

    private FXMLLoader loader;

    public FxmlLoader() {
        loader = new FXMLLoader();
    }

    public FxmlLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public Parent load(URL location, Initializable controller, String... styles) {
        try {
            loader = new FXMLLoader();
            loader.setLocation(location);
            loader.setController(controller);
            Parent parent = loader.load();
            for (String s : styles) {
                URL url = getClass().getResource("/css/" + s + ".css");
                parent.getStylesheets().add(url.toString());
            }
            return parent;
        } catch (IOException e) {
            Log.err(this, e);
            throw new RuntimeException("Could not load FXML");
        }
    }
}
