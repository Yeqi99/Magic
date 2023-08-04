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
        testList.add("给我弄个整数(一个整数 10)");
        testList.add("魔语停止() 给个整数(第二个整数 20)");
        testList.add("给我弄个字符串(一个字符串 第一个小数)");
        testList.add("显示(\n)");
        testList.add("显示(一个字符串) 显示(,)  显示(第二个整数) 显示(,) 显示(第一个小数) 显示(你好)");
        testList.add("");
        Spell spell=new Spell(testList, magicManager);
        spell.execute(new HashMap<>(),new HashMap<>());
    }
}
