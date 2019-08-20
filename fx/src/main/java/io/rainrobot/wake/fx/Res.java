package io.rainrobot.wake.fx;


import io.rainrobot.wake.core.util.Log;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Res {

    public static final String PRESET_ITEM_STYLE = "/css/Item.css";

    public static List<Image> iconColl() { return new Res().iconFiles(); }
    public static String presetItemStyle() { return new Res().get(PRESET_ITEM_STYLE).toString(); }
    public static String icon_48() { return new Res().get("/icon_48.png").toString(); }
    public static String icon_32() { return new Res().get("/icon_32.png").toString(); }
    public static String icon_16() { return new Res().get("/icon_16.png").toString(); }

    public static URL renameDialog() {
        return new Res().get("/fxml/dialog/rename_dialog.fxml");
    }

    public static URL splash() {
        return new Res().get("/fxml/view/splash_screen.fxml");
    }

    public static String getCss(String file) {
        return new Res().get("/css/" + file + ".css").toString();
    }

    public static URL getView(String file) {
        return new Res().get("/fxml/view/" + file + ".fxml");
    }

    public static List<Image> iconForStage() {
        List<Image> list = new ArrayList<>();
        list.add(new Image(Res.icon_16()));
        list.add(new Image(Res.icon_32()));
        list.add(new Image(Res.icon_48()));
        return list;
    }

    private List<Image> iconFiles() {
        ImageInputStream stream = null;
        ImageReader reader = null;
        List<Image> fxImages = null;
        try {
            stream = ImageIO
                    .createImageInputStream(getClass()
                    .getResourceAsStream("/icon.ico"));
            reader = ImageIO.getImageReaders(stream).next();

            reader.setInput(stream);
            int count = reader.getNumImages(true);

            fxImages = new ArrayList<>(count);

            for (int i = 0; i < count; i++) {
                BufferedImage bufferedImage = reader.read(i, null);
                Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
                fxImages.add(fxImage);
            }
        } catch (IOException e) {
            Log.err(this, e);
        } finally {
            if(stream != null) {
                try {
                    stream.close(); // Remember to close/dispose in a finally block
                } catch (IOException e) {
                    Log.err(this , e);
                }
            }
            if(reader != null) reader.dispose();
        }
        return fxImages;
    }

    private URL get(String location) {
        return getClass().getResource(location);
    }
}
