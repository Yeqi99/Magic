import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.object.Spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args){
        MagicManager magicManager=new MagicManager();
        magicManager.getFastExpression().getFunctionManager().register(new TestFunction());
        List<String> testList=new ArrayList<>();
        testList.add("test() test()");
        testList.add("test() test()");
        Spell spell=new Spell(testList, magicManager);
        spell.execute(new HashMap<>(),new HashMap<>());
    }
}
