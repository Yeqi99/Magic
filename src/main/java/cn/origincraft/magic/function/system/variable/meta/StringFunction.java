package cn.origincraft.magic.function.system.variable.meta;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtil;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.BooleanResult;
import dev.rgbmc.expression.results.DoubleResult;
import dev.rgbmc.expression.results.IntegerResult;
import dev.rgbmc.expression.results.StringResult;

import java.util.List;

public class StringFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("STRING_FUNCTION_ARGS_ERROR", "String don't have enough args.");
        }
        FunctionResult functionResult = args.get(0);
        if (functionResult instanceof StringResult v) {
            return new StringResult(v.getString());
        }else if (functionResult instanceof IntegerResult v) {
            return new StringResult(v.getInteger()+"");
        }else if (functionResult instanceof DoubleResult v) {
            return new StringResult(v.getDouble()+"");
        }else if (functionResult instanceof BooleanResult v) {
            return new StringResult(v.getBoolean()+"");
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
        return "string";
    }
}
