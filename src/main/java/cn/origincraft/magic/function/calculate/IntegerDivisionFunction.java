package cn.origincraft.magic.function.calculate;

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

public class IntegerDivisionFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        MagicManager mm = spellContext.getMagicManager();
        List<Object> list = mm
                .getFastExpression()
                .getFunctionManager()
                .parseParaExpression(para);

        // 开始计算的标记
        boolean isFirst = true;
        int result = 1;

        for (Object o : list) {
            int value = 0;

            if (MethodUtil.isFunction(o)) {
                CallableFunction function = (CallableFunction) o;
                StringParameter stringParameter =
                        (StringParameter) function.getParameter();
                spellContext.putExecuteParameter(stringParameter.getString());
                SpellContextResult spellContextResult =
                        (SpellContextResult) function.getFunction().call(new SpellContextParameter(spellContext));
                spellContext = spellContextResult.getSpellContext();
                FunctionResult functionResult = spellContext.getExecuteReturn();

                value = extractValueFromResult(functionResult);
            } else {
                String sValue = (String) o;
                if (spellContext.getVariableMap().containsKey(sValue)) {
                    Object v = spellContext.getVariableMap().get(sValue);
                    value = extractValueFromObject(v);
                } else {
                    if (VariableUtil.tryInt(sValue)) {
                        value = Integer.parseInt(sValue);
                    }
                }
            }

            if (isFirst) {
                result = value;
                isFirst = false;
            } else {
                if (value != 0) {
                    result /= value;
                } else {
                    // 这里应该处理除数为0的情况，可以抛出异常或返回特定结果
                    throw new ArithmeticException("Division by zero");
                }
            }
        }

        spellContext.putExecuteReturn(new FunctionResult.IntResult(result));
        return new SpellContextResult(spellContext);
    }

    private int extractValueFromResult(FunctionResult functionResult) {
        int value = 0;
        if (functionResult instanceof FunctionResult.IntResult v){
            value = v.getInt();
        }
        if (functionResult instanceof FunctionResult.StringResult v){
            if (VariableUtil.tryInt(v.getString())) {
                value = Integer.parseInt(v.getString());
            }
        }
        if (functionResult instanceof FunctionResult.ObjectResult v){
            if (VariableUtil.isInt(v.getObject())){
                value = (int)v.getObject();
            }
        }
        return value;
    }

    private int extractValueFromObject(Object object) {
        int value = 0;
        if (VariableUtil.isInt(object)) {
            value = (int) object;
        }
        if (VariableUtil.isString(object)) {
            if (VariableUtil.tryInt((String) object)) {
                value = Integer.parseInt((String) object);
            }
        }
        return value;
    }

    @Override
    public String getName() {
        return "integerDivide";
    }

    @Override
    public String getType() {
        return "CALCULATE";
    }
}