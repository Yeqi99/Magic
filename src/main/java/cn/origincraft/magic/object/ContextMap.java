package cn.origincraft.magic.object;




public interface ContextMap {

    void putObject(String key, Object value);

    void putVariable(String key, Object value);
    void removeObject(String key);
    void removeVariable(String key);
    Object getObject(String key);
    Object getVariable(String key);
    boolean hasObject(String key);
    boolean hasVariable(String key);
}
