package io.rainrobot.wake.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrderdMap<K, V> implements Map<K, V> {
    Map<K, V> map = new HashMap();
    List<K> keyOrder = new ArrayList();
    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean containsValue(Object o) {
        return map.containsValue(o);
    }

    @Override
    public V get(Object o) {
        return map.get(o);
    }

    @Override
    public V put(K k, V v) {
        map.put(k, v);
        keyOrder.add(k);
        return v;
    }

    @Override
    public V remove(Object o) {
        V removed = map.remove(o);
        keyOrder.remove(0);
        return removed;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> anotherMap) {
        anotherMap.forEach((k, v) -> put(k,v));
    }

    @Override
    public void clear() {
        map.clear();
        keyOrder.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }


    public V getValueAt(int pos) {
       return  map.get(getKeyAt(pos));
    }

    public K getKeyAt(int pos) { return  keyOrder.get(pos); }

    public int indexOf(K val) {
        return keyOrder.indexOf(val);
    }
}
