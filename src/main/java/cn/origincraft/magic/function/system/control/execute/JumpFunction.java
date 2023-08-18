package cn.origincraft.magic.function.system.control.execute;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.IntegerResult;

import java.util.List;

public class JumpFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()){
            spellContext.putExecuteNext(spellContext.getExecuteIndex());
        }
        if (args.get(0) instanceof IntegerResult v) {
            spellContext.putExecuteNext(v.getInteger());
        }
        return new NullResult();
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "jump";
    }
}