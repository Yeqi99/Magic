package cn.origincraft.magic.function.system.variable.magic;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ContextMapResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.ContextMap;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.ObjectResult;


import java.util.List;

public class ContextMapFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("CONTEXT_MAP_FUNCTION_ARGS_ERROR", "ContextMap don't have enough args.");
        }
        if (args.get(0) instanceof ContextMapResult v) {
            return new ContextMapResult(v.getContextMap());
        }else if (args.get(0) instanceof ObjectResult v) {
            if (v.getObject() instanceof ContextMap contextMap) {
                return new ContextMapResult(contextMap);
            }else {
                return new ErrorResult("ERROR_IN_TYPE", "Cannot convert object to ContextMap.");
            }
        }else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "contextMap";
    }
}
