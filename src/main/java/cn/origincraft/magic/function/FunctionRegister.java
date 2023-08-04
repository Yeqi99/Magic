package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.behavior.PrintFunction;
import cn.origincraft.magic.function.target.DoubleFunction;
import cn.origincraft.magic.function.target.IntFunction;
import cn.origincraft.magic.function.target.StringFunction;

public class FunctionRegister {
    public static void regDefault(MagicManager magicManager){
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new IntFunction(),"给我弄个整数","整数","给个整数");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new DoubleFunction(),"给我弄个小数","小数","给个小数");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new StringFunction(),"给我弄个字符串","字符串","给个字符串","一句话");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new PrintFunction(), "显示", "打印");
    }
}
