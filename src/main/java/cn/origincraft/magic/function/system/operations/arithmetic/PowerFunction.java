package cn.origincraft.magic.function.system.operations.arithmetic;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtils;

import java.util.List;

public class PowerFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "PowerFunction function requires at least one argument.");
        }

        if (args.size() < 2) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "PowerFunction requires at least two arguments: base and exponent.");
        }

        FunctionResult baseArg = args.get(0);
        double base = 1.0; // Initialize base to 1 for multiplication

        if (baseArg instanceof ErrorResult) {
            return baseArg;
        }

        if (baseArg instanceof IntegerResult) {
            base = ((IntegerResult) baseArg).getInteger();
        } else if (baseArg instanceof DoubleResult) {
            base = ((DoubleResult) baseArg).getDouble();
        } else if (baseArg instanceof BooleanResult) {
            base = ((BooleanResult) baseArg).getBoolean() ? 1 : 0;
        } else if (baseArg instanceof StringResult) {
            String stringValue = ((StringResult) baseArg).getString();
            if (VariableUtils.tryDouble(stringValue)) {
                base = Double.parseDouble(stringValue);
            } else {
                return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
            }
        } else if (baseArg instanceof ObjectResult) {
            Object objectValue = baseArg.getObject();
            if (objectValue instanceof Integer) {
                base = (Integer) objectValue;
            } else if (objectValue instanceof Double) {
                base = (Double) objectValue;
            } else if (objectValue instanceof Boolean) {
                base = (Boolean) objectValue ? 1 : 0;
            } else if (objectValue instanceof String stringValue) {
                if (stringValue.matches("-?\\d+(\\.\\d+)?")) {
                    base = Double.parseDouble(stringValue);
                } else {
                    return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
                }
            } else {
                return new ErrorResult("ERROR_IN_TYPE", "Cannot convert object to number.");
            }
        }

        double result = base; // Initialize result with the base

        for (int i = 1; i < args.size(); i++) {
            FunctionResult arg = args.get(i);


            if (arg instanceof IntegerResult) {
                result = Math.pow(result, ((IntegerResult) arg).getInteger());
            } else if (arg instanceof DoubleResult) {
                result = Math.pow(result, ((DoubleResult) arg).getDouble());
            } else if (arg instanceof BooleanResult) {
                result = Math.pow(result, ((BooleanResult) arg).getBoolean() ? 1 : 0);
            } else if (arg instanceof StringResult) {
                String stringValue = ((StringResult) arg).getString();
                if (stringValue.matches("-?\\d+(\\.\\d+)?")) {
                    result = Math.pow(result, Double.parseDouble(stringValue));
                } else {
                    return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
                }
            } else if (arg instanceof ObjectResult) {
                Object objectValue = arg.getObject();
                if (objectValue instanceof Integer) {
                    result = Math.pow(result, (Integer) objectValue);
                } else if (objectValue instanceof Double) {
                    result = Math.pow(result, (Double) objectValue);
                } else if (objectValue instanceof Boolean) {
                    result = Math.pow(result, (Boolean) objectValue ? 1 : 0);
                } else if (objectValue instanceof String stringValue) {
                    if (stringValue.matches("-?\\d+(\\.\\d+)?")) {
                        result = Math.pow(result, Double.parseDouble(stringValue));
                    } else {
                        return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
                    }
                } else {
                    return new ErrorResult("ERROR_IN_TYPE", "Cannot convert object to number.");
                }
            }
        }

        if (VariableUtils.hasFractionalPart(result)) {
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
        return "power";
    }
}