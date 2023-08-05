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
        testList.add("var(i 5) var(j double(int(10)))");
        testList.add("print(space(10) int(10) space(10) 相除(int(5) 2 int(3)))");
        Spell spell = new Spell(testList, magicManager);
        spell.execute(new HashMap<>(), new HashMap<>());
    }
}
