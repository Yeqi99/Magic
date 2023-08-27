package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.system.control.IfFunction;
import cn.origincraft.magic.function.system.control.IfNotFunction;
import cn.origincraft.magic.function.system.control.WhileFunction;
import cn.origincraft.magic.function.system.control.execute.*;
import cn.origincraft.magic.function.system.info.ExecuteCountFunction;
import cn.origincraft.magic.function.system.info.MagicWordsIndexFunction;
import cn.origincraft.magic.function.system.info.time.NowFunction;
import cn.origincraft.magic.function.system.io.input.ImportFunction;
import cn.origincraft.magic.function.system.io.input.InputFunction;
import cn.origincraft.magic.function.system.io.output.PrintFunction;
import cn.origincraft.magic.function.system.operations.arithmetic.*;
import cn.origincraft.magic.function.system.operations.comparison.CompareFunction;
import cn.origincraft.magic.function.system.operations.logic.AndFunction;
import cn.origincraft.magic.function.system.operations.logic.NotFunction;
import cn.origincraft.magic.function.system.operations.logic.OrFunction;
import cn.origincraft.magic.function.system.operations.logic.XOrFunction;
import cn.origincraft.magic.function.system.thread.SpellAsyncDaemonExecuteFunction;
import cn.origincraft.magic.function.system.thread.SpellAsyncExecuteFunction;
import cn.origincraft.magic.function.system.variable.container.ListFunction;
import cn.origincraft.magic.function.system.variable.container.MapFunction;
import cn.origincraft.magic.function.system.variable.container.SetFunction;
import cn.origincraft.magic.function.system.variable.define.ObjectDefineFunction;
import cn.origincraft.magic.function.system.variable.define.VariableDefineFunction;
import cn.origincraft.magic.function.system.variable.get.ObjectGetFunction;
import cn.origincraft.magic.function.system.variable.get.VariableGetFunction;
import cn.origincraft.magic.function.system.variable.has.ObjectHasFunction;
import cn.origincraft.magic.function.system.variable.has.VariableHasFunction;
import cn.origincraft.magic.function.system.variable.magic.ContextMapFunction;
import cn.origincraft.magic.function.system.variable.magic.SpellFunction;
import cn.origincraft.magic.function.system.variable.meta.*;
import cn.origincraft.magic.function.system.variable.meta.string.ASCIIFunction;
import cn.origincraft.magic.function.system.variable.meta.string.EnterFunction;
import cn.origincraft.magic.function.system.variable.meta.string.SpaceFunction;


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
        magicManager.getFastExpression().getFunctionManager().register(new ImportFunction());
        // control
        magicManager.getFastExpression().getFunctionManager().register(new IfFunction());
        magicManager.getFastExpression().getFunctionManager().register(new IfNotFunction());
        magicManager.getFastExpression().getFunctionManager().register(new WhileFunction());
        // control.execute
        magicManager.getFastExpression().getFunctionManager().register(new MagicWordsBreakFunction(), "mwbreak");
        magicManager.getFastExpression().getFunctionManager().register(new SpellBreakFunction(), "sbreak");
        magicManager.getFastExpression().getFunctionManager().register(new SpellExecuteFunction(), "spelle");
        magicManager.getFastExpression().getFunctionManager().register(new JumpFunction());
        magicManager.getFastExpression().getFunctionManager().register(new PassFunction());
        // info
        magicManager.getFastExpression().getFunctionManager().register(new ExecuteCountFunction(), "ecount");
        magicManager.getFastExpression().getFunctionManager().register(new MagicWordsIndexFunction(), "eindex");
        // info.time
        magicManager.getFastExpression().getFunctionManager().register(new NowFunction());
        // variable.meta
        magicManager.getFastExpression().getFunctionManager().register(new IntFunction());
        magicManager.getFastExpression().getFunctionManager().register(new DoubleFunction());
        magicManager.getFastExpression().getFunctionManager().register(new BooleanFunction(), "bool");
        magicManager.getFastExpression().getFunctionManager().register(new StringFunction(),"str");
        magicManager.getFastExpression().getFunctionManager().register(new ObjectFunction(),"obj");
        magicManager.getFastExpression().getFunctionManager().register(new LongFunction());
        magicManager.getFastExpression().getFunctionManager().register(new FloatFunction());
        // variable.define
        magicManager.getFastExpression().getFunctionManager().register(new ObjectDefineFunction(), "odef");
        magicManager.getFastExpression().getFunctionManager().register(new VariableDefineFunction(), "vdef");
        // variable.magic
        magicManager.getFastExpression().getFunctionManager().register(new ContextMapFunction(), "cmap");
        magicManager.getFastExpression().getFunctionManager().register(new SpellFunction(), "spell");
        // variable.container
        magicManager.getFastExpression().getFunctionManager().register(new ListFunction());
        magicManager.getFastExpression().getFunctionManager().register(new SetFunction());
        magicManager.getFastExpression().getFunctionManager().register(new MapFunction());
        // variable.get
        magicManager.getFastExpression().getFunctionManager().register(new VariableGetFunction(), "vget");
        magicManager.getFastExpression().getFunctionManager().register(new ObjectGetFunction(), "oget");
        // variable.has
        magicManager.getFastExpression().getFunctionManager().register(new VariableHasFunction(), "vhas");
        magicManager.getFastExpression().getFunctionManager().register(new ObjectHasFunction(), "ohas");
        // variable.meta.string
        magicManager.getFastExpression().getFunctionManager().register(new SpaceFunction());
        magicManager.getFastExpression().getFunctionManager().register(new EnterFunction());
        magicManager.getFastExpression().getFunctionManager().register(new ASCIIFunction());
        // thread
        magicManager.getFastExpression().getFunctionManager().register(new SpellAsyncExecuteFunction(), "spellae");
        magicManager.getFastExpression().getFunctionManager().register(new SpellAsyncDaemonExecuteFunction(), "spellade");
    }
}
