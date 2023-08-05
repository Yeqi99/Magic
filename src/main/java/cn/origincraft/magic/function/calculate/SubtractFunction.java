package cn.origincraft.magic.function.calculate;

import cn.origincraft.magic.interpreter.fastexpression.functions.CallableFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.interpreter.fastexpression.parameters.StringParameter;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.utils.MethodUtil;
import cn.origincraft.magic.utils.VariableUtil;

import java.util.List;

public class SubtractFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        String[] pares = para.split(" ");
        double result = 0;

        // 第一个参数作为被减数
        if (pares.length > 0) {
            String firstParameter = pares[0];
            List<CallableFunction> firstFunctionList = spellContext
                    .getMagicManager()
                    .getFastExpression()
                    .getFunctionManager()
                    .parseExpression(firstParameter);

            if (firstFunctionList.size() > 0) {
                StringParameter sPara =
                        (StringParameter) firstFunctionList.get(0).getParameter();
                spellContext.putExecuteParameter(sPara.getString());
                FunctionResult functionResult = firstFunctionList.get(0).getFunction().call(new SpellContextParameter(spellContext));
                if (functionResult instanceof FunctionResult.DoubleResult) {
                    result = ((FunctionResult.DoubleResult) functionResult).getDouble();
                }
                if (functionResult instanceof FunctionResult.IntResult) {
                    result = ((FunctionResult.IntResult) functionResult).getInt();
                }
            } else {
                if (spellContext.getVariableMap().containsKey(firstParameter)) {
                    Object num = spellContext.getVariableMap().get(firstParameter);
                    if (num instanceof Integer) {
                        result = (int) num;
                    }
                    if (num instanceof Double) {
                        result = (double) num;
                    }
                } else {
                    if (VariableUtil.isDouble(firstParameter)) {
                        result = Double.parseDouble(firstParameter);
                    }
                }
            }
        }

        // 之后的参数都作为减数
        for (int i = 1; i < pares.length; i++) {
            String s = pares[i];
            List<CallableFunction> list = spellContext
                    .getMagicManager()
                    .getFastExpression()
                    .getFunctionManager()
                    .parseExpression(s);
            if (list.size() > 0) {
                StringParameter sPara =
                        (StringParameter) list.get(0).getParameter();
                spellContext.putExecuteParameter(sPara.getString());
                FunctionResult functionResult = list.get(0).getFunction().call(new SpellContextParameter(spellContext));
                if (functionResult instanceof FunctionResult.DoubleResult) {
                    result -= ((FunctionResult.DoubleResult) functionResult).getDouble();
                }
                if (functionResult instanceof FunctionResult.IntResult) {
                    result -= ((FunctionResult.IntResult) functionResult).getInt();
                }
            } else {
                if (spellContext.getVariableMap().containsKey(s)) {
                    Object num = spellContext.getVariableMap().get(s);
                    if (num instanceof Integer) {
                        result -= (int) num;
                    }
                    if (num instanceof Double) {
                        result -= (double) num;
                    }
                } else {
                    if (VariableUtil.isDouble(s)) {
                        result -= Double.parseDouble(s);
                    }
                }
            }
        }

        if (VariableUtil.hasFractionalPart(result)) {
            return new FunctionResult.DoubleResult(result);
        } else {
            return new FunctionResult.IntResult((int) result);
        }
    }

    @Override
    public String getName() {
        return "subtract";
    }

    @Override
    public String getType() {
        return "CALCULATE";
    }
}