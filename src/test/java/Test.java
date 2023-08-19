import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.system.io.output.PrintFunction;
import cn.origincraft.magic.object.ContextMap;
import cn.origincraft.magic.object.NormalContext;
import cn.origincraft.magic.object.Spell;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class Test {
    private static ConcurrentHashMap<String, Object> objectMap=new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Object> variableMap=new ConcurrentHashMap<>();
    private static final NormalContext contextMap = new NormalContext();
    public static void main(String[] args) {
        MagicManager magicManager = new MagicManager();
        List<String> testList = new ArrayList<>();
        long startTime = System.nanoTime();
        testList.add("vdef(start now())");
        testList.add("vdef(i int(0)) vdef(j int(1000000))");
        testList.add("while(comp(i < j)) vdef(start now())  print(i) vdef(i add(i int(1))) vdef(end now()) print(第 i 次循环耗时 sub(end start))");
        testList.add("vdef(end now()) print(总耗时 sub(end start))");
        Spell spell = new Spell(testList, magicManager);

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
