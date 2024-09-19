import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.FunctionRegister;
import cn.origincraft.magic.object.NormalContext;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;


public class Test {

    private static final NormalContext contextMap = new NormalContext();
    public static void main(String[] args) {
        MagicManager magicManager = new MagicManager();
        FunctionRegister.regDefault(magicManager);
        List<String> testList = new ArrayList<>();
        testList.add("vdef(a 123321)");

        testList.add("print(a b c)");
        Spell spell = new Spell(testList, magicManager);
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        SpellContext spellContext= spell.fastExecute(contextMap);
        // 记录结束时间
        long endTime = System.currentTimeMillis();
        // 计算执行时间
        long executionTime = endTime - startTime;

        System.out.println("执行时间: " + executionTime + " 毫秒");
    }

}
