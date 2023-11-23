package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.system.AliasAddFunction;
import cn.origincraft.magic.function.system.RegisterCustomFunction;
import cn.origincraft.magic.function.system.control.*;
import cn.origincraft.magic.function.system.control.execute.*;
import cn.origincraft.magic.function.system.info.*;
import cn.origincraft.magic.function.system.info.time.NowFunction;
import cn.origincraft.magic.function.system.io.input.ImportFunction;
import cn.origincraft.magic.function.system.io.input.InputFunction;
import cn.origincraft.magic.function.system.io.output.PrintFunction;
import cn.origincraft.magic.function.system.operations.arithmetic.*;
import cn.origincraft.magic.function.system.operations.comparison.CompareFunction;
import cn.origincraft.magic.function.system.operations.equal.EqualFunction;
import cn.origincraft.magic.function.system.operations.equal.EqualsIgnoreCaseFunction;
import cn.origincraft.magic.function.system.operations.logic.AndFunction;
import cn.origincraft.magic.function.system.operations.logic.NotFunction;
import cn.origincraft.magic.function.system.operations.logic.OrFunction;
import cn.origincraft.magic.function.system.operations.logic.XOrFunction;
import cn.origincraft.magic.function.system.thread.SpellAsyncDaemonExecuteFunction;
import cn.origincraft.magic.function.system.thread.SpellAsyncExecuteFunction;
import cn.origincraft.magic.function.system.variable.container.ArgumentsFunction;
import cn.origincraft.magic.function.system.variable.container.ListFunction;
import cn.origincraft.magic.function.system.variable.container.MapFunction;
import cn.origincraft.magic.function.system.variable.container.SetFunction;
import cn.origincraft.magic.function.system.variable.define.ObjectDefineFunction;
import cn.origincraft.magic.function.system.variable.define.VariableDefineFunction;
import cn.origincraft.magic.function.system.variable.get.*;
import cn.origincraft.magic.function.system.variable.has.ObjectHasFunction;
import cn.origincraft.magic.function.system.variable.has.VariableHasFunction;
import cn.origincraft.magic.function.system.variable.magic.ArgsSettingFunction;
import cn.origincraft.magic.function.system.variable.magic.ContextMapFunction;
import cn.origincraft.magic.function.system.variable.magic.NormalContextFunction;
import cn.origincraft.magic.function.system.variable.magic.SpellFunction;
import cn.origincraft.magic.function.system.variable.meta.*;




public class FunctionRegister {
    public static void regDefault(MagicManager magicManager) {
        // operations.arithmetic
        magicManager.getFastExpression().getFunctionManager().register(new AdditionFunction(), "add","+");
        magicManager.getFastExpression().getFunctionManager().register(new SubtractionFunction(), "sub","-");
        magicManager.getFastExpression().getFunctionManager().register(new MultiplicationFunction(), "mul","*");
        magicManager.getFastExpression().getFunctionManager().register(new DivisionFunction(), "div","/");
        magicManager.getFastExpression().getFunctionManager().register(new PowerFunction(), "pow","^");
        magicManager.getFastExpression().getFunctionManager().register(new ModulusFunction(), "mod","%");
        magicManager.getFastExpression().getFunctionManager().register(new IntegerDivisionFunction(), "idiv","//");
        magicManager.getFastExpression().getFunctionManager().register(new SelfComputingFunction(),"selfc","@");
        // operations.comparison
        magicManager.getFastExpression().getFunctionManager().register(new CompareFunction(), "comp");
        // operations.logic
        magicManager.getFastExpression().getFunctionManager().register(new AndFunction());
        magicManager.getFastExpression().getFunctionManager().register(new OrFunction());
        magicManager.getFastExpression().getFunctionManager().register(new XOrFunction());
        magicManager.getFastExpression().getFunctionManager().register(new NotFunction());
        // operations.equal
        magicManager.getFastExpression().getFunctionManager().register(new EqualFunction(), "eq");
        magicManager.getFastExpression().getFunctionManager().register(new EqualsIgnoreCaseFunction(), "eqic");
        // io.out
        magicManager.getFastExpression().getFunctionManager().register(new PrintFunction(), "out");
        // io.in
        magicManager.getFastExpression().getFunctionManager().register(new InputFunction(), "in");
        magicManager.getFastExpression().getFunctionManager().register(new ImportFunction());
        // control
        magicManager.getFastExpression().getFunctionManager().register(new IfFunction());
        magicManager.getFastExpression().getFunctionManager().register(new IfNotFunction());
        magicManager.getFastExpression().getFunctionManager().register(new WhileFunction(), "when");
        magicManager.getFastExpression().getFunctionManager().register(new ReturnFunction());
        magicManager.getFastExpression().getFunctionManager().register(new ReturnBreakFunction(), "returnb");
        magicManager.getFastExpression().getFunctionManager().register(new TraversalFunction(), "t", "T");
        magicManager.getFastExpression().getFunctionManager().register(new EvalFunction());
        magicManager.getFastExpression().getFunctionManager().register(new EvalExecuteFunction(), "evale");
        // control.execute
        magicManager.getFastExpression().getFunctionManager().register(new MagicWordsBreakFunction(), "mwbreak");
        magicManager.getFastExpression().getFunctionManager().register(new SpellBreakFunction(), "sbreak");
        magicManager.getFastExpression().getFunctionManager().register(new SpellExecuteFunction(), "spelle");
        magicManager.getFastExpression().getFunctionManager().register(new JumpFunction());
        magicManager.getFastExpression().getFunctionManager().register(new PassFunction());
        magicManager.getFastExpression().getFunctionManager().register(new LoadContextFunction(), "lcontext");
        magicManager.getFastExpression().getFunctionManager().register(new ArgsSpellExecuteFunction(), "argsse");
        magicManager.getFastExpression().getFunctionManager().register(new WaitFunction(),"sleep");
        // info
        magicManager.getFastExpression().getFunctionManager().register(new ExecuteCountFunction(), "ecount");
        magicManager.getFastExpression().getFunctionManager().register(new MagicWordsIndexFunction(), "eindex");
        magicManager.getFastExpression().getFunctionManager().register(new IsNullFunction());
        magicManager.getFastExpression().getFunctionManager().register(new RangeFunction());
        magicManager.getFastExpression().getFunctionManager().register(new TypeFunction());
        magicManager.getFastExpression().getFunctionManager().register(new LengthFunction(),"len");
        magicManager.getFastExpression().getFunctionManager().register(new SelfFunction());
        // info.time
        magicManager.getFastExpression().getFunctionManager().register(new NowFunction());
        // variable.meta
        magicManager.getFastExpression().getFunctionManager().register(new BooleanFunction(), "bool");
        magicManager.getFastExpression().getFunctionManager().register(new StringFunction(), "str");
        magicManager.getFastExpression().getFunctionManager().register(new NumberFunction(), "num");
        magicManager.getFastExpression().getFunctionManager().register(new OriginStringFunction(), "ostr");
        magicManager.getFastExpression().getFunctionManager().register(new NullFunction());
        // variable.define
        magicManager.getFastExpression().getFunctionManager().register(new ObjectDefineFunction(), "odef");
        magicManager.getFastExpression().getFunctionManager().register(new VariableDefineFunction(), "vdef");
        // variable.magic
        magicManager.getFastExpression().getFunctionManager().register(new ContextMapFunction(), "cmap");
        magicManager.getFastExpression().getFunctionManager().register(new SpellFunction(), "spell");
        magicManager.getFastExpression().getFunctionManager().register(new NormalContextFunction(), "ncontext");
        magicManager.getFastExpression().getFunctionManager().register(new ArgsSettingFunction(),"asetting");
        // variable.container
        magicManager.getFastExpression().getFunctionManager().register(new ListFunction());
        magicManager.getFastExpression().getFunctionManager().register(new SetFunction());
        magicManager.getFastExpression().getFunctionManager().register(new MapFunction());
        magicManager.getFastExpression().getFunctionManager().register(new ArgumentsFunction(), "args");
        // variable.get
        magicManager.getFastExpression().getFunctionManager().register(new VariableGetFunction(), "vget");
        magicManager.getFastExpression().getFunctionManager().register(new ObjectGetFunction(), "oget");
        magicManager.getFastExpression().getFunctionManager().register(new ContextGetFunction(), "cget");
        magicManager.getFastExpression().getFunctionManager().register(new ContainerRandomGetFunction(), "crandomg");
        magicManager.getFastExpression().getFunctionManager().register(new UnpackFunction(),"upk");
        magicManager.getFastExpression().getFunctionManager().register(new GetFunction());
        // variable.has
        magicManager.getFastExpression().getFunctionManager().register(new VariableHasFunction(), "vhas");
        magicManager.getFastExpression().getFunctionManager().register(new ObjectHasFunction(), "ohas");
        // thread
        magicManager.getFastExpression().getFunctionManager().register(new SpellAsyncExecuteFunction(), "spellae");
        magicManager.getFastExpression().getFunctionManager().register(new SpellAsyncDaemonExecuteFunction(), "spellade");
        // normal
        magicManager.getFastExpression().getFunctionManager().register(new AliasAddFunction(), "alias", "aliases", "别名");
        magicManager.getFastExpression().getFunctionManager().register(new RegisterCustomFunction(), "rcf");
    }
}
