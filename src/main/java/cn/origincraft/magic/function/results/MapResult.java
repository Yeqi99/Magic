package cn.origincraft.magic.function.results;

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
}
