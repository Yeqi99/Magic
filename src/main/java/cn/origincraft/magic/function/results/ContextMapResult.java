package cn.origincraft.magic.function.results;

import cn.origincraft.magic.object.ContextMap;

public class ContextMapResult extends ObjectResult {
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

    @Override
    public String toString() {
        StringBuilder s= new StringBuilder();
        s.append("Variables:\n");
        for (String variableName : getContextMap().getVariableNames()) {
            s.append(variableName).append("=").append(getContextMap().getVariable(variableName).toString()).append("\n");
        }
        s.append("Objects:\n");
        for (String objectName : getContextMap().getObjectNames()) {
            s.append(objectName).append("=").append(getContextMap().getObject(objectName).toString()).append("\n");
        }
        return s.toString();
    }
    @Override
    public int len() {
        return getContextMap().getObjectNames().size();
    }
}
