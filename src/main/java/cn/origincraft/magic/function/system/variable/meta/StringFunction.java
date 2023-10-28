package cn.origincraft.magic.function.system.variable.meta;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ListResult;
import cn.origincraft.magic.function.results.ObjectResult;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class StringFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("STRING_FUNCTION_ARGS_ERROR", "String don't have enough args.");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (FunctionResult functionResult : args) {
            if (functionResult instanceof ListResult) {
                List<?> list = ((ListResult) functionResult).getList();
                for (Object o : list) {
                    if (o instanceof String) {
                        stringBuilder.append(o).append("\n");
                    }
                }
            } else if (functionResult instanceof ObjectResult v) {
                stringBuilder.append(v.getObject().toString());
            } else {
                return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
            }
        }
        return new StringResult(stringBuilder.toString());
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "string";
    }
}
