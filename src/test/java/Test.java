import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.system.out.PrintFunction;
import cn.origincraft.magic.object.ContextMap;
import cn.origincraft.magic.object.Spell;
import com.sun.tools.javac.Main;

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
        long startTime = System.nanoTime();
        testList.add("var(i int(0))  var(j int(1000000))");
        testList.add("while(compare(i < j) print(i enter()) var( i add(i 1)))");
        //testList.add("while(比较(i < j) print(i) var( i add(i 1) ) )");
        //testList.add("if(compare(i < j) jump(魔语序号())) ifnot(compare(i < j) 魔语停止()) print(你好 空格() 世界 i 回车()) var( i add(i 1) ) )");
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
