package cn.origincraft.magic.function.results;

import dev.rgbmc.expression.functions.FunctionResult;
import java.util.Map;

public class MapResult extends FunctionResult {
    private final Map<String,Object> value;

    public MapResult(Map<String,Object> value) {
        this.value = value;
    }

    public Map<String,Object> getMap() {
        return value;
    }
}
