import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.FunctionRegister;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.object.Spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Test {
    public static void main(String[] args){
        MagicManager magicManager=new MagicManager();
        magicManager.getFastExpression().getFunctionManager().register(new TestFunction());
        List<String> testList=new ArrayList<>();
        testList.add("显示(相加(1 -2 3.5 7 8 9 -100.5))");
        Spell spell=new Spell(testList, magicManager);
        spell.execute(new HashMap<>(),new HashMap<>());
    }
}
