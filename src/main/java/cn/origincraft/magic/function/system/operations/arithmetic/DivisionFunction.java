package cn.origincraft.magic.function.system.operations.arithmetic;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtils;

import java.util.List;

public class DivisionFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size() < 2) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "Division function requires at least two arguments.");
        }

        FunctionResult dividendArg = args.get(0);
        if (dividendArg instanceof ErrorResult) {
            return dividendArg;
        }

        double dividend;
        if (dividendArg instanceof IntegerResult) {
            dividend = ((IntegerResult) dividendArg).getInteger();
        } else if (dividendArg instanceof DoubleResult) {
            dividend = ((DoubleResult) dividendArg).getDouble();
        } else if (dividendArg instanceof BooleanResult) {
            dividend = ((BooleanResult) dividendArg).getBoolean() ? 1 : 0;
        } else if (dividendArg instanceof StringResult) {
            String stringValue = ((StringResult) dividendArg).getString();
            if (VariableUtils.tryDouble(stringValue)) {
                dividend = Double.parseDouble(stringValue);
            } else {
                return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
            }
        } else if (dividendArg instanceof ObjectResult) {
            Object objectValue = dividendArg.getObject();
            if (objectValue instanceof Integer) {
                dividend = (Integer) objectValue;
            } else if (objectValue instanceof Double) {
                dividend = (Double) objectValue;
            } else if (objectValue instanceof Boolean) {
                dividend = (Boolean) objectValue ? 1 : 0;
            } else if (objectValue instanceof String stringValue) {
                if (VariableUtils.tryDouble(stringValue)) {
                    dividend = Double.parseDouble(stringValue);
                } else {
                    return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
                }
            } else {
                return new ErrorResult("ERROR_IN_TYPE", "Cannot convert object to number.");
            }
        } else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }

        double result = dividend;

        for (int i = 1; i < args.size(); i++) {
            FunctionResult divisorArg = args.get(i);
            if (divisorArg instanceof ErrorResult) {
                return divisorArg;
            }

            double divisor;
            if (divisorArg instanceof IntegerResult) {
                divisor = ((IntegerResult) divisorArg).getInteger();
            } else if (divisorArg instanceof DoubleResult) {
                divisor = ((DoubleResult) divisorArg).getDouble();
            } else if (divisorArg instanceof BooleanResult) {
                divisor = ((BooleanResult) divisorArg).getBoolean() ? 1 : 0;
            } else if (divisorArg instanceof StringResult) {
                String stringValue = ((StringResult) divisorArg).getString();
                if (stringValue.matches("-?\\d+(\\.\\d+)?")) {
                    divisor = Double.parseDouble(stringValue);
                } else {
                    return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
                }
            } else if (divisorArg instanceof ObjectResult) {
                Object objectValue = (divisorArg).getObject();
                if (objectValue instanceof Integer) {
                    divisor = (Integer) objectValue;
                } else if (objectValue instanceof Double) {
                    divisor = (Double) objectValue;
                } else if (objectValue instanceof Boolean) {
                    divisor = (Boolean) objectValue ? 1 : 0;
                } else if (objectValue instanceof String stringValue) {
                    if (stringValue.matches("-?\\d+(\\.\\d+)?")) {
                        divisor = Double.parseDouble(stringValue);
                    } else {
                        return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
                    }
                } else {
                    return new ErrorResult("ERROR_IN_TYPE", "Cannot convert object to number.");
                }
            } else {
                return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
            }

            if (divisor == 0) {
                return new ErrorResult("DIVISION_BY_ZERO", "Division by zero is not allowed.");
            }

            result /= divisor;
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
        return "division";
    }
}
