package cn.origincraft.magic.object;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class NormalContext implements ContextMap{
    private ConcurrentHashMap<String, Object> objectMap=new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Object> variableMap=new ConcurrentHashMap<>();
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

    @Override
    public Set<String> getVariableNames() {
        return variableMap.keySet();
    }

    @Override
    public Set<String> getObjectNames() {
        return objectMap.keySet();
    }

    @Override
    public Set<Object> getVariables() {
        return new HashSet<>(variableMap.values());
    }

    @Override
    public Set<Object> getObjects() {
        return new HashSet<>(objectMap.values());
    }

    @Override
    public void clearVariable() {
        variableMap.clear();
    }

    @Override
    public void clearObject() {
        objectMap.clear();
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
