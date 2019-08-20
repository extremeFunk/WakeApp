package io.rainrobot.wake.util;

import java.util.HashMap;
import java.util.function.Function;

import io.rainrobot.wake.core.Idabel;

public class Maps {
    public static <V extends Idabel> HashMap<Integer,V> getIdeabelMap(V[] array) {
        return getMap(array, (idabel -> idabel.getId()));
    }

    public static <K, V> HashMap<K, V> getMap(V[] array, Function<V, K> keyGen) {
        HashMap<K, V> map = new HashMap();
        for (V v : array) {
            map.put(keyGen.apply(v), v);
        }
        return map;
    }
}
