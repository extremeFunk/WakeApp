package io.rainrobot.wake.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Collections {
    public static <K> OrderdMap<K, String> getStringMap(List<K> keys, Function<K, String> parser) {
        OrderdMap<K, String> map = new OrderdMap();
        keys.forEach((val) -> {
            map.put(val, parser.apply(val));
        });
        return map;
    }

    public static List<Integer> createIntSequence(int min, int max) {
        return createIntSequence(min, max, 1);
    }

    public static List<Integer> createIntSequence(int min, int max, int interval) {
        List<Integer> list = new ArrayList();
        for (int i = min; i <= max; i += interval) list.add(i);
        return list;
    }
}
