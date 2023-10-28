package cn.origincraft.magic.function.system.variable.container;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ObjectResult;
import cn.origincraft.magic.function.results.SetResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("SET_FUNCTION_ARGS_ERROR", "Set don't have enough args.");
        }
        Set<Object> resultSet = new HashSet<>();
        for (FunctionResult functionResult : args) {
            if (functionResult instanceof ObjectResult v) {
                resultSet.add(v.getObject());
            } else {
                resultSet.add(functionResult);
            }
        }
        return new SetResult(resultSet);
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "set";
    }
}
