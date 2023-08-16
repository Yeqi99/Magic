package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.newsystem.io.input.InputFunction;
import cn.origincraft.magic.function.newsystem.io.output.PrintFunction;
import cn.origincraft.magic.function.newsystem.operations.arithmetic.*;
import cn.origincraft.magic.function.newsystem.operations.comparison.CompareFunction;


public class FunctionRegister {
    public static void regDefault(MagicManager magicManager){
        // operations.arithmetic
        magicManager.getFastExpression().getFunctionManager().register(new AdditionFunction(),"add");
        magicManager.getFastExpression().getFunctionManager().register(new SubtractionFunction(),"sub");
        magicManager.getFastExpression().getFunctionManager().register(new MultiplyFunction(), "mul");
        magicManager.getFastExpression().getFunctionManager().register(new DivisionFunction(), "div");
        magicManager.getFastExpression().getFunctionManager().register(new PowerFunction(), "pow");
        magicManager.getFastExpression().getFunctionManager().register(new ModulusFunction(),"mod");
        magicManager.getFastExpression().getFunctionManager().register(new IntegerDivisionFunction(),"idiv");
        // operations.comparison
        magicManager.getFastExpression().getFunctionManager().register(new CompareFunction(),"compare");
        // io.in
        magicManager.getFastExpression().getFunctionManager().register(new PrintFunction(),"out");
        magicManager.getFastExpression().getFunctionManager().register(new InputFunction(),"in");
    }
}
