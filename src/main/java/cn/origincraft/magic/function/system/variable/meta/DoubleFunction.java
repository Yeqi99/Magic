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

public class DoubleFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("DOUBLE_FUNCTION_ARGS_ERROR", "Double don't have enough args.");
        }
        FunctionResult functionResult = args.get(0);
        if (functionResult instanceof DoubleResult v) {
            return new DoubleResult(v.getDouble());
        }else if (functionResult instanceof StringResult v) {
            if (VariableUtil.tryDouble(v.getString())) {
                return new DoubleResult(Double.parseDouble(v.getString()));
            }else {
                return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
            }
        }else if (functionResult instanceof IntegerResult v) {
            return new DoubleResult(v.getInteger());
        }else if (functionResult instanceof BooleanResult v) {
            return new DoubleResult(v.getBoolean() ? 1 : 0);
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
        return "double";
    }
}