package io.rainrobot.wake.fx.view.itemlist;

import io.rainrobot.wake.fx.Res;
import io.rainrobot.wake.fx.view.component.FxmlLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.net.URL;

public abstract class FxmlItem implements ViewItem, Initializable {

    FxmlLoader loader = new FxmlLoader();

    int index;

    @Override
    public Node getView() {
        Parent view = loader.load(getUrl(), this);
        view.getStylesheets().removeAll();
        view.getStylesheets().add(Res.presetItemStyle());
        return view;
    }

    protected abstract URL getUrl();

    @Override
    public Integer getIndex() { return index; }

    @Override
    public void setIndex(Integer index) { this.index = index; }
}
