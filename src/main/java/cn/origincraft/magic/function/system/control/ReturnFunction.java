package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ArgumentsResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;

import java.util.List;

public class ReturnFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("RETURN_FUNCTION_ARGS_ERROR", "Return don't have enough args.");
        }
        spellContext.putSpellReturn(args.get(0));
        return new NullResult();
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "return";
    }
}
