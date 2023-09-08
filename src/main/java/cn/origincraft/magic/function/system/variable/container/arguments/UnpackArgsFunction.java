package cn.origincraft.magic.function.system.variable.container.arguments;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ArgumentsResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.system.control.IfFunction;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;

import java.util.List;

public class UnpackArgsFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments don't have enough args.");
        }
        FunctionResult argsResult = args.get(0);
        if (!(argsResult instanceof ArgumentsResult)) {
            return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments type error.");
        }
        ArgumentsResult argumentsResult = (ArgumentsResult) argsResult;
        List<FunctionResult> argList= argumentsResult.getArgs();
        for (int i=0;i<argList.size();i++) {
            spellContext.getContextMap().putVariable("arg"+i,argList.get(i));
        }
        return new NullResult();
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "unpackArgs";
    }
}