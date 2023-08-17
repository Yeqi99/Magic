package cn.origincraft.magic.function.system.control.execute;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.SpellResult;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;


import java.util.List;

public class SpellExecuteFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()){
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "SpellExecute function requires at least one argument.");
        }

        for (FunctionResult arg : args) {
            if (arg instanceof ErrorResult) {
                return arg;
            }
            if (arg instanceof SpellResult spellResult){
                Spell spell = spellResult.getSpell();
                spell.execute(spellContext.getContextMap());
            }
        }
        return null;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "spellExecute";
    }
}
