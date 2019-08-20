package io.rainrobot.wake.fx.view.itemlist;

import javafx.scene.Node;

import java.util.Date;

public interface ViewItem {
    Node getView();
    Integer getId();
    void setIndex(Integer index);
    Integer getIndex();
}
