package io.rainrobot.wake.fx.view.component;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import io.rainrobot.wake.core.util.Log;
import io.rainrobot.wake.fx.Main;
import io.rainrobot.wake.fx.Res;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainStageMgr {

	private Node thinkLayer;
	private Node content;
	private Map<String, StackPane> rootMap = new HashMap<>();
	private Stage stage;
	private FxmlLoader loader;
	private static final int thinkModeMinimum = 2;
	private int thinkMode = 0;

	private static final double BLUR_AMOUNT = 10;
	private final Effect frostEffect = new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 3);
	private boolean isDisabeld = false;
    private StackPane root;

    public MainStageMgr(Stage stage, FxmlLoader loader) {
		this.stage = stage;
		stage.setOnCloseRequest(e -> Main.isAppShowing = false);
		stage.setResizable(false);
        this.loader = loader;
		setStage();
    }

	public void load(URL fxmlLocation, Initializable view) {
        String rootKey = fxmlLocation.getFile();
		if(rootMap.containsKey(rootKey)) {
			root = rootMap.get(rootKey);
		}
		else {
			root = new StackPane();
			root.getStylesheets().add(Res.getCss("Main"));
			root.getStylesheets().add(Res.getCss("PrimaryPortlet"));
			content = loader.load(fxmlLocation, view);
			root.getChildren().add(content);
			root.getChildren().add(getThinkLayer());
		}
		if (isDisabeld) { disable();}
	}

	public void showFullScreen() {
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        double width = visualBounds.getWidth();
        double height = visualBounds.getHeight();
//		stage.setScene(new Scene(root, width, height));
		setStage();
		stage.show();
	}

	public void show() {
		stage.setScene(new Scene(root));
        stage.show();
	}

    public void setStage() {
        stage.setTitle("WakeApp");
		stage.getIcons().add(new Image(Res.icon_16()));
		stage.getIcons().add(new Image(Res.icon_32()));
		stage.getIcons().add(new Image(Res.icon_48()));
    }

    public void close() {
		stage.close();
	}

	public void disable() {
		isDisabeld = true;
		content.setDisable(true);
		content.setEffect(frostEffect);
	}

	private Node getThinkLayer() {
		if (thinkLayer == null) {
			thinkLayer = loader.load(Res.getView("splash_screen"), null);
            ((Parent) thinkLayer).getStylesheets().add(Res.getCss("ThinkDialog"));
            thinkLayer.setVisible(false);
		}
		return thinkLayer;
	}

	public void enable() {
		isDisabeld = false;
		content.setDisable(false);
		content.setEffect(null);
	}


	public void startThinkMode() {
		thinkMode++;
		Log.d(this, "start think mode - count = " + thinkMode);
		if(thinkMode < thinkModeMinimum) {
			disable();
			thinkLayer.setVisible(true);
		}
	}

	public void stopThinkMode() {
		if(thinkMode < thinkModeMinimum) {
            enable();
			thinkLayer.setVisible(false);
		}
		thinkMode--;
		Log.d(this, "stop think mode - count = " + thinkMode);
	}

	public double getXPos() {
		return stage.getX();
	}

	public double getYPost() {
		return stage.getY();
	}

	public double getHeight() {
		return stage.getHeight();
	}

	public double getWidth() {
		return stage.getWidth();
	}
}
