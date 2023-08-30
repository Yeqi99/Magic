package cn.origincraft.magic.function.system.info;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.BooleanResult;

import java.util.List;

public class IsNullFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()){
            return new ErrorResult("ISNULL_FUNCTION_ARGS_ERROR", "IsNull don't have enough args.");
        }
        if (args.get(0) instanceof NullResult){
            return new BooleanResult(true);
        }else {
            return new BooleanResult(false);
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "isnull";
    }
}
