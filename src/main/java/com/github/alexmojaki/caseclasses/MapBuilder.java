package com.github.alexmojaki.caseclasses;

import java.util.Map;

class MapBuilder<V> extends AbstractResultBuilder {

    private Map<String, V> map;
    private Class<V> type;

    static <T extends Map<String, V>, V> void putValues(CaseClass obj, T map, Class<V> type) {
        MapBuilder builder = new MapBuilder();
        builder.map = map;
        builder.type = type;
        obj.buildResult(builder);
    }

    static <T extends Map> void putValues(CaseClass obj, T map) {
        putValues(obj, map, Object.class);
    }

    @Override
    protected void simpleAdd(String name, Object value) {
        map.put(name, type.cast(value));
    }

}
