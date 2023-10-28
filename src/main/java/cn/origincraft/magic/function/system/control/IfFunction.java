package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.BooleanResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class IfFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("IF_FUNCTION_ARGS_ERROR", "If don't have enough args.");
        }
        if (args.get(0) instanceof BooleanResult v) {
            if (!v.getBoolean()){
                spellContext.putExecuteIndexAllow(spellContext.getExecuteIndex(),false);
            }
        }
        return new NullResult();
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "if";
    }
}
