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

public class DivideFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        String[] pares = para.split(" ");

        // 第一个参数作为被除数
        if (pares.length > 0) {
            String firstParameter = pares[0];
            List<CallableFunction> firstFunctionList = spellContext
                    .getMagicManager()
                    .getFastExpression()
                    .getFunctionManager()
                    .parseExpression(firstParameter);

            double result = 0;

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

            // 其他参数都作为除数
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
                        double divisor = ((FunctionResult.DoubleResult) functionResult).getDouble();
                        if (divisor != 0) {
                            result /= divisor;
                        } else {
                            // 如果除数为零或参数不合法，则返回0
                            return new FunctionResult.ObjectResult(0);
                        }
                    }
                    if (functionResult instanceof FunctionResult.IntResult) {
                        int divisor = ((FunctionResult.IntResult) functionResult).getInt();
                        if (divisor != 0) {
                            result /= divisor;
                        } else {
                            // 如果除数为零或参数不合法，则返回0
                            return new FunctionResult.ObjectResult(0);
                        }
                    }
                } else {
                    if (spellContext.getVariableMap().containsKey(s)) {
                        Object num = spellContext.getVariableMap().get(s);
                        if (num instanceof Integer) {
                            int divisor = (int) num;
                            if (divisor != 0) {
                                result /= divisor;
                            } else {
                                // 如果除数为零或参数不合法，则返回0
                                return new FunctionResult.ObjectResult(0);
                            }
                        }
                        if (num instanceof Double) {
                            double divisor = (double) num;
                            if (divisor != 0) {
                                result /= divisor;
                            } else {
                                // 如果除数为零或参数不合法，则返回0
                                return new FunctionResult.ObjectResult(0);
                            }
                        }
                    } else {
                        if (VariableUtil.isDouble(s)) {
                            double divisor = Double.parseDouble(s);
                            if (divisor != 0) {
                                result /= divisor;
                            } else {
                                // 如果除数为零或参数不合法，则返回0
                                return new FunctionResult.ObjectResult(0);
                            }
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

        // 如果参数为空，则返回0
        return new FunctionResult.ObjectResult(0);
    }

    @Override
    public String getName() {
        return "divide";
    }

    @Override
    public String getType() {
        return "CALCULATE";
    }
}