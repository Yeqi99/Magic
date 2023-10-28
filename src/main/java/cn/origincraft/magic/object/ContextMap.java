package cn.origincraft.magic.object;


import java.util.Set;

public interface ContextMap {

    void putObject(String key, Object value);

    void putVariable(String key, Object value);

    void removeObject(String key);

    void removeVariable(String key);

    Object getObject(String key);

    Object getVariable(String key);

    boolean hasObject(String key);

    boolean hasVariable(String key);

    Set<String> getVariableNames();

    Set<String> getObjectNames();

    Set<Object> getVariables();

    Set<Object> getObjects();

    void clearVariable();

    void clearObject();

}
