import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.object.Spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class Test {
    public static void main(String[] args) {
        MagicManager magicManager = new MagicManager();
        magicManager.getFastExpression().getFunctionManager().register(new TestFunction());
        List<String> testList = new ArrayList<>();
        testList.add("变量(控制变量 整数(输入(请输入循环次数)))");
        testList.add("变量(i 1)");
        testList.add("如果是(比较(i < 控制变量) 跳到(魔语序号()) )  print(i) 变量(i 相加(i 1))");
        Spell spell = new Spell(testList, magicManager);
        spell.execute(new HashMap<>(), new HashMap<>());
    }
}
