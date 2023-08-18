package cn.origincraft.magic.function.system.variable.meta.string;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.MethodUtil;
import cn.origincraft.magic.utils.VariableUtil;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.IntegerResult;
import dev.rgbmc.expression.results.StringResult;

import java.util.List;

public class ASCIIFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("ASCII_FUNCTION_ARGS_ERROR", "ASCII don't have enough args.");
        }
        FunctionResult functionResult = args.get(0);
        if (functionResult instanceof StringResult v) {
            if (VariableUtil.tryInt(v.getString())){
                return new StringResult(MethodUtil.getEnter(Integer.parseInt(v.getString())));
            }else {
                return new ErrorResult("ASCII_FUNCTION_ARGS_ERROR", "ASCII args must be a number.");
            }
        }else if (functionResult instanceof IntegerResult v){
            return new StringResult(((char)v.getInteger())+"");
        }
        return new ErrorResult("ASCII_FUNCTION_ARGS_ERROR", "ASCII args must be a number.");
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "ascii";
    }
}
