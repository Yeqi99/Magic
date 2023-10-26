package cn.origincraft.magic.function.results;

import dev.rgbmc.expression.functions.FunctionResult;
import java.util.Map;

public class MapResult extends FunctionResult {
    private final Map<?,?> value;

    public MapResult(Map<?,?> value) {
        this.value = value;
    }

    public Map<?,?> getMap() {
        return value;
    }
}
