package cn.origincraft.magic.function.system.calculate;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.interpreter.fastexpression.functions.CallableFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.interpreter.fastexpression.parameters.StringParameter;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;
import cn.origincraft.magic.utils.VariableUtil;

import java.util.List;
// 比较运算
public class CompareFunction implements FastFunction {

    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        MagicManager mm = spellContext.getMagicManager();
        List<Object> list = mm.getFastExpression()
                .getFunctionManager()
                .parseParaExpression(para);

        if (list.size() != 3) {
            spellContext.putExecuteReturn(new FunctionResult.BooleanResult(false));
            return new SpellContextResult(spellContext);
        }

        double firstValue = extractDoubleValueFromObject(list.get(0), spellContext);
        String compareType = (String) list.get(1);
        double secondValue = extractDoubleValueFromObject(list.get(2), spellContext);

        boolean result = compareValues(firstValue, compareType, secondValue);
        spellContext.putExecuteReturn(new FunctionResult.BooleanResult(result));

        return new SpellContextResult(spellContext);
    }

    private double extractDoubleValueFromObject(Object o, SpellContext spellContext) {
        try {
            if (MethodUtil.isFunction(o)) {
                CallableFunction function = (CallableFunction) o;
                StringParameter stringParameter = (StringParameter) function.getParameter();
                spellContext.putExecuteParameter(stringParameter.getString());
                SpellContextResult spellContextResult = (SpellContextResult) function.getFunction().call(new SpellContextParameter(spellContext));
                spellContext = spellContextResult.getSpellContext();
                FunctionResult functionResult = spellContext.getExecuteReturn();

                if (functionResult instanceof FunctionResult.DoubleResult v) {
                    return v.getDouble();
                } else if (functionResult instanceof FunctionResult.IntResult v) {
                    return v.getInt();
                } else if (functionResult instanceof FunctionResult.BooleanResult v) {
                    return v.getBoolean() ? 1 : 0;
                }
            } else {
                String stringValue = (String) o;
                if (VariableUtil.tryDouble(stringValue)) {
                    return Double.parseDouble(stringValue);
                } else if (spellContext.getVariableMap().containsKey(stringValue)) {
                    Object variableValue = spellContext.getVariableMap().get(stringValue);
                    if (variableValue instanceof Double) {
                        return (double) variableValue;
                    } else if (variableValue instanceof Integer) {
                        return (int) variableValue;
                    } else if (variableValue instanceof Boolean) {
                        return (Boolean) variableValue ? 1 : 0;
                    }
                }
            }
        } catch (Exception e) {
            // Ignore exceptions and return 0
        }
        return 0;
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