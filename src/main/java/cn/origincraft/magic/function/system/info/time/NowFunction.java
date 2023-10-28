package cn.origincraft.magic.function.system.info.time;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.DoubleResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class NowFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        return new DoubleResult(System.nanoTime() / 1e9);
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
