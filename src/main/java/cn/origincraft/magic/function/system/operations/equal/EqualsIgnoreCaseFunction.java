package cn.origincraft.magic.function.system.operations.equal;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.BooleanResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class EqualsIgnoreCaseFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size() < 2) {
            return new ErrorResult("EQUAL_FUNCTION_ARGS_ERROR", "EqualsIgnoreCase don't have enough args.");
        }
        FunctionResult first = args.get(0);
        FunctionResult second = args.get(1);
        if (first instanceof StringResult && second instanceof StringResult) {
            String firstString = ((StringResult) first).getString();
            String secondString = ((StringResult) second).getString();
            return new BooleanResult(firstString.equalsIgnoreCase(secondString));
        } else {
            return new ErrorResult("EQUAL_FUNCTION_ARGS_ERROR", "EqualsIgnoreCase don't support this type.");
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "equalsIgnoreCase";
    }
}
