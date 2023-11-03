package cn.origincraft.magic.object;


import cn.origincraft.magic.function.results.ObjectResult;

import java.util.Map;

public class SpellContextResult extends ObjectResult {

    public SpellContextResult(SpellContext spellContext) {
        super(spellContext);
    }

    public SpellContext getSpellContext() {
        return (SpellContext) getObject();
    }

    @Override
    public String getName() {
        return "SpellContext";
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Map.Entry<String, Object> stringObjectEntry : getSpellContext().getExecuteResultMap().entrySet()) {
            s.append(stringObjectEntry.getKey()).append(":").append(stringObjectEntry.getValue().toString()).append(",");
        }
        s.append("Variables:\n");
        for (String variableName : getSpellContext().getContextMap().getVariableNames()) {
            s.append(variableName).append("=").append(getSpellContext().getContextMap().getVariable(variableName).toString()).append("\n");
        }
        s.append("Objects:\n");
        for (String objectName : getSpellContext().getContextMap().getObjectNames()) {
            s.append(objectName).append("=").append(getSpellContext().getContextMap().getObject(objectName).toString()).append("\n");
        }
        return s.toString();
    }
}
