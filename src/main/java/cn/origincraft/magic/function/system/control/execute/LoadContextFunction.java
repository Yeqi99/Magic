package cn.origincraft.magic.function.system.control.execute;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ContextMapResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class LoadContextFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "LoadContext function requires at least one argument.");
        }
        FunctionResult context = args.get(0);
        if (context instanceof ContextMapResult) {
            spellContext.setContextMap(((ContextMapResult) context).getContextMap());
            return new NullResult();
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
        return "loadContext";
    }
}
