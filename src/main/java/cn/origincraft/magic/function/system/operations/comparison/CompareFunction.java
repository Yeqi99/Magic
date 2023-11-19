package cn.origincraft.magic.function.system.operations.comparison;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtils;

import java.util.List;

public class CompareFunction extends NormalFunction {

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size() != 3) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "CompareFunction function requires exactly three arguments.");
        }

        FunctionResult firstArg = args.get(0);
        FunctionResult secondArg = args.get(1);
        FunctionResult thirdArg = args.get(2);

        double firstValue = extractDoubleValue(firstArg, spellContext);
        double secondValue = extractDoubleValue(thirdArg, spellContext);
        String compareType = extractCompareType(secondArg);

        boolean result = compareValues(firstValue, compareType, secondValue);
        return new BooleanResult(result);
    }

    private double extractDoubleValue(FunctionResult arg, SpellContext spellContext) {
        if (arg instanceof DoubleResult) {
            return ((DoubleResult) arg).getDouble();
        } else if (arg instanceof IntegerResult) {
            return ((IntegerResult) arg).getInteger();
        } else if (arg instanceof BooleanResult) {
            return ((BooleanResult) arg).getBoolean() ? 1 : 0;
        } else if (arg instanceof StringResult) {
            String stringValue = ((StringResult) arg).getString();
            if (VariableUtils.tryDouble(stringValue)) {
                return Double.parseDouble(stringValue);
            } else if (spellContext.getContextMap().hasVariable(stringValue)) {
                Object variableValue = spellContext.getContextMap().getVariable(stringValue);
                if (variableValue instanceof Double) {
                    return (double) variableValue;
                } else if (variableValue instanceof Integer) {
                    return (int) variableValue;
                } else if (variableValue instanceof Boolean) {
                    return (Boolean) variableValue ? 1 : 0;
                }
            }
        }
        return 0;
    }

    private String extractCompareType(FunctionResult arg) {
        if (arg instanceof StringResult) {
            return ((StringResult) arg).getString();
        }
        return "";
    }

    private boolean compareValues(double firstValue, String compareType, double secondValue) {
        try {
            return switch (compareType) {
                case "==" -> Double.compare(firstValue, secondValue) == 0;
                case ">" -> Double.compare(firstValue, secondValue) > 0;
                case ">=" -> Double.compare(firstValue, secondValue) >= 0;
                case "<" -> Double.compare(firstValue, secondValue) < 0;
                case "<=" -> Double.compare(firstValue, secondValue) <= 0;
                case "!=" -> Double.compare(firstValue, secondValue) != 0;
                default -> false;
            };
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "compare";
    }

    @Override
    public String getType() {
        return "CALCULATE";
    }

}
