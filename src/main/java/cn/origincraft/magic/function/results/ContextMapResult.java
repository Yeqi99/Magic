package cn.origincraft.magic.function.results;

import cn.origincraft.magic.object.ContextMap;

public class ContextMapResult extends ObjectResult{
    public ContextMapResult(ContextMap contextMap) {
        super(contextMap);
    }

    public ContextMap getContextMap() {
        return (ContextMap) getObject();
    }

    @Override
    public String getName() {
        return "ContextMap";
    }
}
