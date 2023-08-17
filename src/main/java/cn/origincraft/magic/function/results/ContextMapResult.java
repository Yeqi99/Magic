package cn.origincraft.magic.function.results;

import cn.origincraft.magic.object.ContextMap;
import dev.rgbmc.expression.functions.FunctionResult;

public class ContextMapResult extends FunctionResult {
    private final ContextMap contextMap;

    public ContextMapResult(ContextMap contextMap) {
        this.contextMap = contextMap;
    }

    public ContextMap getContextMap() {
        return contextMap;
    }
}
