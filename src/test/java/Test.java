import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.object.ContextMap;
import cn.origincraft.magic.object.Spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Test {
    private static Map<String,Object> objectMap=new HashMap<>();
    private static Map<String,Object> variableMap=new HashMap<>();
    public static void main(String[] args) {
        MagicManager magicManager = new MagicManager();
        magicManager.getFastExpression().getFunctionManager().register(new TestFunction());
        List<String> testList = new ArrayList<>();
        testList.add("print(abc)");
        Spell spell = new Spell(testList, magicManager);
        ContextMap contextMap=new ContextMap() {
            @Override
            public Map<String, Object> getObjectMap() {
                return Test.getObjectMap();
            }

            @Override
            public Map<String, Object> getVariableMap() {
                return Test.getVariableMap();
            }

            @Override
            public void putObject(String key, Object value) {
                Test.getObjectMap().put(key, value);
            }

            @Override
            public void putVariable(String key, Object value) {
                Test.getVariableMap().put(key, value);
            }

            @Override
            public void setObjectMap(Map<String, Object> objectMap) {
                Test.setObjectMap(objectMap);
            }

            @Override
            public void setVariableMap(Map<String, Object> variableMap) {
                Test.setVariableMap(variableMap);
            }
        };
        contextMap.putObject("a", "Hello World!" );
        contextMap.putObject("test", spell);
        List<String> testList2 = new ArrayList<>();
        testList2.add("spell(test)");
        Spell spell2 = new Spell(testList2, magicManager);
        spell2.execute(contextMap);
    }

    public static Map<String, Object> getObjectMap() {
        return objectMap;
    }

    public static void setObjectMap(Map<String, Object> objectMap) {
        Test.objectMap = objectMap;
    }

    public static Map<String, Object> getVariableMap() {
        return variableMap;
    }

    public static void setVariableMap(Map<String, Object> variableMap) {
        Test.variableMap = variableMap;
    }
}
