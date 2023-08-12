package cn.origincraft.magic.object;


import java.util.concurrent.ConcurrentHashMap;

public interface ContextMap {
    ConcurrentHashMap<String, Object> getObjectMap();
    ConcurrentHashMap<String, Object> getVariableMap();

    void putObject(String key, Object value);

    void putVariable(String key, Object value);

    void setObjectMap(ConcurrentHashMap<String, Object> objectMap);

    void setVariableMap(ConcurrentHashMap<String, Object> variableMap);
}
