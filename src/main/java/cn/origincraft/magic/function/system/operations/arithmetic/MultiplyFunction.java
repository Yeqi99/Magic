package cn.origincraft.magic.function.system.operations.arithmetic;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtil;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.*;

import java.util.List;

public class MultiplyFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "MultiplyFunction function requires at least one argument.");
        }

        double result = 1.0; // Initialize result to 1 for multiplication

        for (FunctionResult arg : args) {
            if (arg instanceof ErrorResult) {
                return arg;
            }

            if (arg instanceof IntegerResult) {
                result *= ((IntegerResult) arg).getInteger();
            } else if (arg instanceof DoubleResult) {
                result *= ((DoubleResult) arg).getDouble();
            } else if (arg instanceof BooleanResult) {
                result *= ((BooleanResult) arg).getBoolean() ? 1 : 0;
            } else if (arg instanceof StringResult) {
                String stringValue = ((StringResult) arg).getString();
                if (VariableUtil.tryDouble(stringValue)) {
                    result *= Double.parseDouble(stringValue);
                } else {
                    return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
                }
            } else if (arg instanceof ObjectResult) {
                Object objectValue = ((ObjectResult) arg).getObject();
                if (objectValue instanceof Integer) {
                    result *= (Integer) objectValue;
                } else if (objectValue instanceof Double) {
                    result *= (Double) objectValue;
                } else if (objectValue instanceof Boolean) {
                    result *= (Boolean) objectValue ? 1 : 0;
                } else if (objectValue instanceof String stringValue) {
                    if (stringValue.matches("-?\\d+(\\.\\d+)?")) {
                        result *= Double.parseDouble(stringValue);
                    } else {
                        return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
                    }
                } else {
                    return new ErrorResult("ERROR_IN_TYPE", "Cannot convert object to number.");
                }
            }
        }

        if (VariableUtil.hasFractionalPart(result)) {
            return new DoubleResult(result);
        } else {
            return new IntegerResult((int) result);
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "multiplication";
    }
}
