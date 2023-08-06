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
        testList.add("变量(控制变量 0)");
        testList.add("如果是(比较(控制变量 < 200) 跳到(魔语序号())) 显示(控制变量 回车(1)) 变量(控制变量 相加(控制变量 1))");
        Spell spell = new Spell(testList, magicManager);
        spell.execute(new HashMap<>(), new HashMap<>());
    }
}
