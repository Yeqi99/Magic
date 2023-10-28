package cn.origincraft.magic.function.system.control.execute;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.IntegerResult;
import cn.origincraft.magic.function.results.SpellResult;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class SpellExecuteFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {

        if (args.isEmpty()) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "SpellExecute function requires at least one argument.");
        }
        int count = 0;

        for (FunctionResult arg : args) {
            if (arg instanceof SpellResult spellResult) {
                Spell spell = spellResult.getSpell();
                spell.execute(spellContext.getContextMap());
                count++;
            }
        }
        // 返回执行统计
        return new IntegerResult(count);
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
