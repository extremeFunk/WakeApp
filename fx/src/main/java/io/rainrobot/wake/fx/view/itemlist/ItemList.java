package io.rainrobot.wake.fx.view.itemlist;

import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemList<T extends ViewItem> extends VBox {
    Map<Integer, T> idItemMap = new HashMap<>();

    public void setItems(List<T> list) {
        list.forEach((itm) -> {
            add(itm);
        });
    }

    public void add(T item) {
        item.setIndex(getNextIndex());
        idItemMap.put(item.getId(), item);
        super.getChildren().add(item.getView());
    }

    private int getNextIndex() {
        return super.getChildren().size();
    }

    public T getItem(int id) {
        return idItemMap.get(id);
    }

    public void remove(int id) {
        int index = getIndex(id);
        super.getChildren().remove(index);
        idItemMap.remove(id);
        idItemMap.values().stream()
                .filter(itm -> (itm.getIndex() > index))
                .forEach(itm -> itm.setIndex(itm.getIndex() - 1));
    }

    public int getIndex(int id) {
        return getItem(id).getIndex();
    }

    public boolean containsId(Integer id) {
        return idItemMap.containsKey(id);
    }

    public void clear() {
        super.getChildren().clear();
        idItemMap.clear();
    }
}

