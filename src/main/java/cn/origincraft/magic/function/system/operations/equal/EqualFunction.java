package cn.origincraft.magic.function.system.operations.equal;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.BooleanResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ObjectResult;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class EqualFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size() < 2) {
            return new ErrorResult("EQUAL_FUNCTION_ARGS_ERROR", "Equal don't have enough args.");
        }
        FunctionResult first = args.get(0);
        FunctionResult second = args.get(1);
        if (first instanceof StringResult && second instanceof StringResult) {
            String firstString = ((StringResult) first).getString();
            String secondString = ((StringResult) second).getString();
            return new BooleanResult(firstString.equals(secondString));
        }
        if (first instanceof ObjectResult && second instanceof ObjectResult) {
            Object firstObject = (first).getObject();
            Object secondObject = (second).getObject();
            return new BooleanResult(firstObject.equals(secondObject));
        }
        return new ErrorResult("EQUAL_FUNCTION_ARGS_ERROR", "Equal don't support this type.");
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "equal";
    }
}
