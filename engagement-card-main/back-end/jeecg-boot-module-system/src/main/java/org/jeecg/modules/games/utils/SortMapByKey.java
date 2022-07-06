package org.jeecg.modules.games.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class SortMapByKey implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        return ((String) o1).compareTo((String) o2);
    }

    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> sortMap = new TreeMap<>(new SortMapByKey());
        sortMap.putAll(map);
        return sortMap;
    }

}
