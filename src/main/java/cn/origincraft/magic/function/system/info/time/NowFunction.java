package cn.origincraft.magic.function.system.info.time;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.DoubleResult;
import dev.rgbmc.expression.results.IntegerResult;

import java.util.List;

public class NowFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        return new DoubleResult(System.nanoTime()/ 1e9);
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "now";
    }
}
