package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.behavior.PrintFunction;
import cn.origincraft.magic.function.calculate.*;
import cn.origincraft.magic.function.constraint.BreakMagicWordsFunction;
import cn.origincraft.magic.function.constraint.BreakSpellFunction;
import cn.origincraft.magic.function.system.*;
import cn.origincraft.magic.function.target.*;

public class FunctionRegister {
    public static void regDefault(MagicManager magicManager){
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new VariableFunction(), "var", "变量");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new DoubleFunction(), "double", "小数");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new IntFunction(), "int", "小数");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new StringFunction(), "str", "字符串");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new BooleanFunction(), "bool", "是否");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new PrintFunction(), "显示", "打印");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new SpaceFunction(), "空格");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new BreakMagicWordsFunction(), "魔语停止");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new BreakSpellFunction(), "魔咒停止");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new AddFunction(), "相加","加","a");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new SubtractFunction(), "相减","减","s");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new DivideFunction(), "相除","除","d");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new MultiplyFunction(), "相乘","乘","m");
    }
}
