package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.system.control.IfFunction;
import cn.origincraft.magic.function.system.control.IfNotFunction;
import cn.origincraft.magic.function.system.control.execute.MagicWordsBreakFunction;
import cn.origincraft.magic.function.system.control.execute.SpellBreakFunction;
import cn.origincraft.magic.function.system.control.execute.SpellExecuteFunction;
import cn.origincraft.magic.function.system.io.input.InputFunction;
import cn.origincraft.magic.function.system.io.output.PrintFunction;
import cn.origincraft.magic.function.system.operations.arithmetic.*;
import cn.origincraft.magic.function.system.operations.comparison.CompareFunction;
import cn.origincraft.magic.function.system.operations.logic.AndFunction;
import cn.origincraft.magic.function.system.operations.logic.NotFunction;
import cn.origincraft.magic.function.system.operations.logic.OrFunction;
import cn.origincraft.magic.function.system.operations.logic.XOrFunction;
import cn.origincraft.magic.function.system.variable.define.VariableDefineFunction;
import cn.origincraft.magic.function.system.variable.magic.ContextMapFunction;
import cn.origincraft.magic.function.system.variable.meta.*;


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
        magicManager.getFastExpression().getFunctionManager().register(new CompareFunction(),"comp");
        // operations.logic
        magicManager.getFastExpression().getFunctionManager().register(new AndFunction());
        magicManager.getFastExpression().getFunctionManager().register(new OrFunction());
        magicManager.getFastExpression().getFunctionManager().register(new XOrFunction());
        magicManager.getFastExpression().getFunctionManager().register(new NotFunction());
        // io.out
        magicManager.getFastExpression().getFunctionManager().register(new PrintFunction(),"out");
        // io.in
        magicManager.getFastExpression().getFunctionManager().register(new InputFunction(),"in");
        // control
        magicManager.getFastExpression().getFunctionManager().register(new IfFunction(), "i");
        magicManager.getFastExpression().getFunctionManager().register(new IfNotFunction(), "it");
        // control.execute
        magicManager.getFastExpression().getFunctionManager().register(new MagicWordsBreakFunction(), "mwbreak");
        magicManager.getFastExpression().getFunctionManager().register(new SpellBreakFunction(), "sbreak");
        magicManager.getFastExpression().getFunctionManager().register(new SpellExecuteFunction(), "spelle");

        // control.execute
        magicManager.getFastExpression().getFunctionManager().register(new MagicWordsBreakFunction(), "mwbreak");
        magicManager.getFastExpression().getFunctionManager().register(new SpellBreakFunction(), "sbreak");
        // variable.meta
        magicManager.getFastExpression().getFunctionManager().register(new IntFunction());
        magicManager.getFastExpression().getFunctionManager().register(new DoubleFunction());
        magicManager.getFastExpression().getFunctionManager().register(new BooleanFunction(), "bool");
        magicManager.getFastExpression().getFunctionManager().register(new StringFunction(),"str");
        magicManager.getFastExpression().getFunctionManager().register(new ObjectFunction(),"obj");
        // variable.define
        magicManager.getFastExpression().getFunctionManager().register(new ObjectFunction(), "odef");
        magicManager.getFastExpression().getFunctionManager().register(new VariableDefineFunction(), "vdef");
        // variable.magic
        magicManager.getFastExpression().getFunctionManager().register(new ContextMapFunction(), "cmap");

    }
}
