package cn.origincraft.magic.function.system.operations.logic;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.BooleanResult;

import java.util.List;

public class XOrFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()){
            return new BooleanResult(false);
        }
        boolean result=false;
        for (FunctionResult arg : args) {
            if (arg instanceof BooleanResult value){
                result^=value.getBoolean();
            }
        }
        return new BooleanResult(result);
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "xor";
    }
}
