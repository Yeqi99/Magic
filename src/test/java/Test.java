import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.system.io.output.PrintFunction;
import cn.origincraft.magic.object.ContextMap;
import cn.origincraft.magic.object.Spell;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class Test {
    private static ConcurrentHashMap<String, Object> objectMap=new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Object> variableMap=new ConcurrentHashMap<>();
    public static void main(String[] args) {
        MagicManager magicManager = new MagicManager();
        List<String> testList = new ArrayList<>();
        long startTime = System.nanoTime();
        testList.add("if(comp(2 > 3)) print(not(comp(3 > 2) comp(4 > 3)))");
        testList.add("it(comp(3 > 2)) print(comp(3 > 2))");
        Spell spell = new Spell(testList, magicManager);
        ContextMap contextMap= new ContextMap() {

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
        };
        spell.execute(contextMap);

        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1e9; // 转换为秒

        System.out.printf("主程序代码执行时间：%.6f 秒%n", executionTime);
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
