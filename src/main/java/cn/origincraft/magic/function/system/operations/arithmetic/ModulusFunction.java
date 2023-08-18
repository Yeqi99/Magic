package cn.origincraft.magic.function.system.operations.arithmetic;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtil;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.*;

import java.util.List;

public class ModulusFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size() < 2) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "Modulus function requires at least two arguments.");
        }

        int result = 0;
        boolean isFirstArg = true;

        for (FunctionResult arg : args) {
            if (arg instanceof ErrorResult) {
                return arg;
            }

            int value;
            if (arg instanceof IntegerResult) {
                value = ((IntegerResult) arg).getInteger();
            } else if (arg instanceof DoubleResult) {
                value = (int) ((DoubleResult) arg).getDouble();
            } else if (arg instanceof BooleanResult) {
                value = ((BooleanResult) arg).getBoolean() ? 1 : 0;
            } else if (arg instanceof StringResult) {
                String stringValue = ((StringResult) arg).getString();
                if (stringValue.matches("-?\\d+")) {
                    value = Integer.parseInt(stringValue);
                } else {
                    return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
                }
            } else if (arg instanceof ObjectResult) {
                Object objectValue = ((ObjectResult) arg).getObject();
                if (objectValue instanceof Integer) {
                    value = (Integer) objectValue;
                } else if (objectValue instanceof Double) {
                    value = (int) ((Double) objectValue).doubleValue();
                } else if (objectValue instanceof Boolean) {
                    value = (Boolean) objectValue ? 1 : 0;
                } else if (objectValue instanceof String stringValue) {
                    if (VariableUtil.tryInt(stringValue)) {
                        value = Integer.parseInt(stringValue);
                    } else {
                        return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
                    }
                } else {
                    return new ErrorResult("ERROR_IN_TYPE", "Cannot convert object to number.");
                }
            } else {
                return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
            }

            if (isFirstArg) {
                result = value;
                isFirstArg = false;
            } else {
                if (value == 0) {
                    return new ErrorResult("MODULUS_BY_ZERO", "Modulus by zero is not allowed.");
                }
                result %= value;
            }
        }

        return new IntegerResult(result);
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "modulus";
    }
}