package cn.origincraft.magic.function.results;

import dev.rgbmc.expression.functions.FunctionResult;
import java.util.Map;

public class MapResult extends FunctionResult {
    private final Map<Object,Object> value;

    public MapResult(Map<Object,Object> value) {
        this.value = value;
    }

    public Map<Object,Object> getMap() {
        return value;
    }
}
