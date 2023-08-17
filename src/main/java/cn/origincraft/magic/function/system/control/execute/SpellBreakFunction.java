package cn.origincraft.magic.function.system.control.execute;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;

import java.util.List;

public class SpellBreakFunction extends NormalFunction {
    @Override
    public String getName() {
        return "spellBreak";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        spellContext.putExecuteBreak(true);
        return new NullResult();
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
