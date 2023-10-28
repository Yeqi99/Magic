package cn.origincraft.magic.function.system.operations.logic;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.BooleanResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class NotFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new BooleanResult(false);
        }
        if (args.get(0) instanceof BooleanResult value) {
            return new BooleanResult(!value.getBoolean());
        } else {
            return new ErrorResult("ERROR_IN_TYPE", "Cannot convert object to boolean.");
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "not";
    }
}
