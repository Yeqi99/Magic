import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.system.out.PrintFunction;
import cn.origincraft.magic.object.ContextMap;
import cn.origincraft.magic.object.Spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Test {
    private static ConcurrentHashMap<String, Object> objectMap=new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Object> variableMap=new ConcurrentHashMap<>();
    public static void main(String[] args) {
        MagicManager magicManager = new MagicManager();
        List<String> testList = new ArrayList<>();

        testList.add("var(i int(0)) var(j int(10))");
        testList.add("while(compare(i < j) print(i) var( i add(i 1) ) )");
       // testList.add("if(compare(i < j) jump(魔语序号())) print(i) var( i add(i 1) ) )");
        Spell spell = new Spell(testList, magicManager);
        ContextMap contextMap=new ContextMap() {
            @Override
            public ConcurrentHashMap<String, Object> getObjectMap() {
                return Test.getObjectMap();
            }

            @Override
            public ConcurrentHashMap<String, Object> getVariableMap() {
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
            public void setObjectMap(ConcurrentHashMap<String, Object> objectMap) {
                Test.setObjectMap(objectMap);
            }

            @Override
            public void setVariableMap(ConcurrentHashMap<String, Object> variableMap) {
                Test.setVariableMap(variableMap);
            }
        };
        contextMap.putObject("a", "Hello World!" );
        contextMap.putObject("test", spell);
        List<String> testList2 = new ArrayList<>();
        testList2.add("print(abc)");
        testList2.add("aspell(test)");
        testList2.add("print(a i)");

        Spell spell2 = new Spell(testList2, magicManager);
        spell2.execute(contextMap);
    }


    public static ConcurrentHashMap<String, Object> getObjectMap() {
        return objectMap;
    }

    public static void setObjectMap(ConcurrentHashMap<String, Object> objectMap) {
        Test.objectMap = objectMap;
    }

    public static ConcurrentHashMap<String, Object> getVariableMap() {
        return variableMap;
    }

    public static void setVariableMap(ConcurrentHashMap<String, Object> variableMap) {
        Test.variableMap = variableMap;
    }
}
