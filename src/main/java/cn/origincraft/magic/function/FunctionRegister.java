package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.behavior.PrintFunction;
import cn.origincraft.magic.function.target.IntFunction;

public class FunctionRegister {
    public static void regDefault(MagicManager magicManager){
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new IntFunction(),"整数");

        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new PrintFunction(), "显示", "打印");
    }
}