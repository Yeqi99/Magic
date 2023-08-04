import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.FunctionRegister;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
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
        testList.add("显示(第一个数) 显示(,) 显示(第二个数) 显示(你好) 整数(第一个数 1) 整数(第二个数 2)");
        Spell spell=new Spell(testList, magicManager);
        spell.execute(new HashMap<>(),new HashMap<>());
    }
}
