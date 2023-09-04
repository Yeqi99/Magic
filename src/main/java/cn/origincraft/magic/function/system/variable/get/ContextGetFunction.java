package cn.origincraft.magic.function.system.variable.get;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ContextMapResult;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;

import java.util.List;

public class ContextGetFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        return new ContextMapResult(spellContext.getContextMap());
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "contextGet";
    }
}
