package cn.origincraft.magic.function.system.variable.meta;

import cn.origincraft.magic.function.FunctionRegister;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.ResultUtils;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.*;

import java.util.List;

public class ObjectFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("INT_FUNCTION_ARGS_ERROR", "Object don't have enough args.");
        }
        FunctionResult functionResult = args.get(0);
        return new ObjectResult(ResultUtils.reduction(functionResult));
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "object";
    }
}
