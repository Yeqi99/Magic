package cn.origincraft.magic.function.system.control.execute;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ArgumentsResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.SpellResult;
import cn.origincraft.magic.object.NormalContext;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;

import java.util.List;

public class ArgsSpellExecuteFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size()<2) {
            return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments don't have enough args.");
        }
        FunctionResult spell = args.get(0);
        FunctionResult arguments = args.get(1);
        if (spell instanceof SpellResult) {
            if (arguments instanceof ArgumentsResult) {
                Spell spell1 = ((SpellResult) spell).getSpell();
                List<FunctionResult> args1 = ((ArgumentsResult) arguments).getArgs();
                NormalContext context = new NormalContext();
                context.putVariable("args", new ArgumentsResult(args1));
                SpellContext spellContext1= spell1.execute(context);
                if (!spellContext1.hasSpellReturn()){
                    return new NullResult();
                }else {
                    return spellContext1.getSpellReturn();
                }
            }else {
                return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments type error.");
            }
        }else {
            return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments type error.");
        }

    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "argsSpellExecute";
    }
}
