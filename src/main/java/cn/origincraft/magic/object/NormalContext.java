package cn.origincraft.magic.object;

import java.util.concurrent.ConcurrentHashMap;

public class NormalContext implements ContextMap{
    private ConcurrentHashMap<String, Object> objectMap;
    private ConcurrentHashMap<String, Object> variableMap;
    @Override
    public void putObject(String key, Object value) {
        getObjectMap().put(key, value);
    }

    @Override
    public void putVariable(String key, Object value) {
        getVariableMap().put(key, value);
    }

    @Override
    public void removeObject(String key) {
        getObjectMap().remove(key);
    }

    @Override
    public void removeVariable(String key) {
        getVariableMap().remove(key);
    }

    @Override
    public Object getObject(String key) {
        return getObjectMap().get(key);
    }

    @Override
    public Object getVariable(String key) {
        return getVariableMap().get(key);
    }

    @Override
    public boolean hasObject(String key) {
        return getObjectMap().containsKey(key);
    }

    @Override
    public boolean hasVariable(String key) {
        return getVariableMap().containsKey(key);
    }

    public ConcurrentHashMap<String, Object> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(ConcurrentHashMap<String, Object> objectMap) {
        this.objectMap = objectMap;
    }

    public ConcurrentHashMap<String, Object> getVariableMap() {
        return variableMap;
    }

    public void setVariableMap(ConcurrentHashMap<String, Object> variableMap) {
        this.variableMap = variableMap;
    }
}
