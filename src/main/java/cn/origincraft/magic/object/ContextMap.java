package cn.origincraft.magic.object;


import java.util.concurrent.ConcurrentHashMap;

public interface ContextMap {
    ConcurrentHashMap<String, Object> getObjectMap();

    void setObjectMap(ConcurrentHashMap<String, Object> objectMap);

    ConcurrentHashMap<String, Object> getVariableMap();

    void setVariableMap(ConcurrentHashMap<String, Object> variableMap);

    void putObject(String key, Object value);

    void putVariable(String key, Object value);
}
