package io.rainrobot.wake.fx.view.dialog;

import io.rainrobot.wake.fx.view.component.FxmlLoader;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.net.URL;

public abstract class FxmlDialog extends Dialog implements Initializable {

    private FxmlLoader loader;

    public FxmlDialog(MainStageMgr mainStageMgr) {
        super(mainStageMgr);
    }

    @Override
    protected Parent getContent() {
        this.loader = new FxmlLoader();
        return loader.load(getUrl(), this, getCss());
    }

    protected String[] getCss() { return new String[]{"Main"}; }

    protected abstract URL getUrl();
}
