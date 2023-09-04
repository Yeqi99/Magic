package cn.origincraft.magic.function.system.operations.arithmetic;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtil;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.*;

import java.util.List;

public class SubtractionFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size() < 2) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "Subtraction function requires at least two arguments.");
        }

        FunctionResult firstArg = args.get(0);
        if (firstArg instanceof ErrorResult) {
            return firstArg;
        }

        double result;
        if (firstArg instanceof IntegerResult) {
            result = ((IntegerResult) firstArg).getInteger();
        } else if (firstArg instanceof DoubleResult) {
            result = ((DoubleResult) firstArg).getDouble();
        } else if (firstArg instanceof BooleanResult) {
            result = ((BooleanResult) firstArg).getBoolean() ? 1 : 0;
        } else if (firstArg instanceof StringResult) {
            String stringValue = ((StringResult) firstArg).getString();
            if (VariableUtil.tryDouble(stringValue)) {
                result = Double.parseDouble(stringValue);
            } else {
                return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
            }
        } else if (firstArg instanceof ObjectResult) {
            Object objectValue = ((ObjectResult) firstArg).getObject();
            if (objectValue instanceof Integer) {
                result = (Integer) objectValue;
            } else if (objectValue instanceof Double) {
                result = (Double) objectValue;
            } else if (objectValue instanceof Boolean) {
                result = (Boolean) objectValue ? 1 : 0;
            } else if (objectValue instanceof String stringValue) {
                if (stringValue.matches("-?\\d+(\\.\\d+)?")) {
                    result = Double.parseDouble(stringValue);
                } else {
                    return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
                }
            } else {
                return new ErrorResult("ERROR_IN_TYPE", "Cannot convert object to number.");
            }
        } else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }

        for (int i = 1; i < args.size(); i++) {
            FunctionResult arg = args.get(i);


            if (arg instanceof IntegerResult) {
                result -= ((IntegerResult) arg).getInteger();
            } else if (arg instanceof DoubleResult) {
                result -= ((DoubleResult) arg).getDouble();
            } else if (arg instanceof BooleanResult) {
                result -= ((BooleanResult) arg).getBoolean() ? 1 : 0;
            } else if (arg instanceof StringResult) {
                String stringValue = ((StringResult) arg).getString();
                if (stringValue.matches("-?\\d+(\\.\\d+)?")) {
                    result -= Double.parseDouble(stringValue);
                } else {
                    return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
                }
            } else if (arg instanceof ObjectResult) {
                Object objectValue = ((ObjectResult) arg).getObject();
                if (objectValue instanceof Integer) {
                    result -= (Integer) objectValue;
                } else if (objectValue instanceof Double) {
                    result -= (Double) objectValue;
                } else if (objectValue instanceof Boolean) {
                    result -= (Boolean) objectValue ? 1 : 0;
                } else if (objectValue instanceof String) {
                    String stringValue = (String) objectValue;
                    if (stringValue.matches("-?\\d+(\\.\\d+)?")) {
                        result -= Double.parseDouble(stringValue);
                    } else {
                        return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
                    }
                } else {
                    return new ErrorResult("ERROR_IN_TYPE", "Cannot convert object to number.");
                }
            } else {
                return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
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
        return "subtraction";
    }
}