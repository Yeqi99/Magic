package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.system.in.InputFunction;
import cn.origincraft.magic.function.system.string.EnterFunction;
import cn.origincraft.magic.function.system.out.PrintFunction;
import cn.origincraft.magic.function.system.control.BreakMagicWordsFunction;
import cn.origincraft.magic.function.system.control.BreakSpellFunction;
import cn.origincraft.magic.function.system.control.JumpFunction;
import cn.origincraft.magic.function.system.control.PassFunction;
import cn.origincraft.magic.function.system.calculate.*;
import cn.origincraft.magic.function.system.control.ForFunction;
import cn.origincraft.magic.function.system.control.IfFunction;
import cn.origincraft.magic.function.system.control.IfNotFunction;
import cn.origincraft.magic.function.system.information.ExecuteCountFunction;
import cn.origincraft.magic.function.system.information.MagicWordsIndexFunction;
import cn.origincraft.magic.function.system.string.SpaceFunction;
import cn.origincraft.magic.function.system.variable.*;

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
                .register(new IntFunction(), "int", "整数");
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
                .register(new InputFunction(), "输入");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new SpaceFunction(), "空格");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new EnterFunction(), "回车");
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
                .register(new MagicWordsIndexFunction(), "魔语序号");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new ExecuteCountFunction(), "执行统计");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new JumpFunction(), "跳到");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new PassFunction(), "跳过");
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
                .register(new IntegerDivisionFunction(), "整除");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new ModulusFunction(), "取模","取余");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new MultiplyFunction(), "相乘","乘","m");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new PowerFunction(), "幂","求方");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new IfFunction(), "如果是");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new ForFunction(), "当");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new IfNotFunction(), "如果否");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new CompareFunction(), "比较");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new ListFunction(), "列表");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new DictionaryFunction(), "字典");
        magicManager
                .getFastExpression()
                .getFunctionManager()
                .register(new SetFunction(), "集合");
    }
}
