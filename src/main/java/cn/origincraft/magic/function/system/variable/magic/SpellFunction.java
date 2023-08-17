package cn.origincraft.magic.function.system.variable.magic;

import cn.origincraft.magic.function.OriginFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;

import java.util.List;

public class SpellFunction extends OriginFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<String> args) {
        if (args.isEmpty()) {
            return new ErrorResult("SPELL_FUNCTION_ARGS_ERROR", "Spell don't have enough args.");
        }

        return null;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "spell";
    }
}
