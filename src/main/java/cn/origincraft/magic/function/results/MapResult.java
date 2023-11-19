package cn.origincraft.magic.function.results;

import java.util.HashMap;
import java.util.Map;

public class MapResult extends ObjectResult {

    public MapResult(Map<?, ?> value) {
        super(value);
    }

    public Map<?, ?> getMap() {
        return (Map<?, ?>) getObject();
    }


    @Override
    public String getName() {
        return "Map";
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Map.Entry<?, ?> entry : getMap().entrySet()) {
            s.append(entry.getKey().toString()).append(":").append(entry.getValue().toString()).append(",");
        }
        return s.toString();
    }
    public Map<Object,Object> getObjectMap(){
        Map<?, ?> originalMap = getMap();

        Map<Object, Object> convertedMap = new HashMap<>();

        for (Map.Entry<?, ?> entry : originalMap.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            convertedMap.put(key, value);
        }
        return convertedMap;
    }
}
