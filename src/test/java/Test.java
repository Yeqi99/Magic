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
        magicManager.getFastExpression().getAliasesManager().registerAliases("print","打印");
        testList.add("print(i) print(,) print(j) print(你好) int(i 1) int(j 2)");
        Spell spell=new Spell(testList, magicManager);
        spell.execute(new HashMap<>(),new HashMap<>());
    }
}
