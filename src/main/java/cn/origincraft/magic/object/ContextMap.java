package cn.origincraft.magic.object;

import java.util.Map;

public interface ContextMap {
    Map<String, Object> getObjectMap();
    Map<String, Object> getVariableMap();

    void putObject(String key, Object value);

    void putVariable(String key, Object value);

    void setObjectMap(Map<String, Object> objectMap);

    void setVariableMap(Map<String, Object> variableMap);
}
