package cn.origincraft.magic.function.system.operations.logic;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.BooleanResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class AndFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new BooleanResult(false);
        }
        for (FunctionResult arg : args) {
            if (arg instanceof BooleanResult value) {
                if (!value.getBoolean()) {
                    return new BooleanResult(false);
                }
            }
        }
        return new BooleanResult(true);
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "and";
    }
}
