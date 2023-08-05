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
//除法运算
public class DivideFunction implements FastFunction {
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
        double result = 1;

        for (Object o : list) {
            double value = 0;

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
                    if (VariableUtil.tryDouble(sValue)) {
                        value = Double.parseDouble(sValue);
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

        if (VariableUtil.hasFractionalPart(result)) {
            spellContext.putExecuteReturn(new FunctionResult.DoubleResult(result));
        } else {
            spellContext.putExecuteReturn(new FunctionResult.IntResult((int) result));
        }
        return new SpellContextResult(spellContext);
    }

    private double extractValueFromResult(FunctionResult functionResult) {
        double value = 0;
        if (functionResult instanceof FunctionResult.DoubleResult v){
            value = v.getDouble();
        }
        if (functionResult instanceof FunctionResult.IntResult v){
            value = v.getInt();
        }
        if (functionResult instanceof FunctionResult.StringResult v){
            if (VariableUtil.tryDouble(v.getString())){
                value = Double.parseDouble(v.getString());
            }
        }
        if (functionResult instanceof FunctionResult.ObjectResult v){
            if (VariableUtil.isDouble(v.getObject())){
                value = (double)v.getObject();
            }
            if (VariableUtil.isInt(v.getObject())){
                value = (int)v.getObject();
            }
        }
        return value;
    }

    private double extractValueFromObject(Object object) {
        double value = 0;
        if (VariableUtil.isDouble(object)) {
            value = (double) object;
        }
        if (VariableUtil.isInt(object)){
            value = (int) object;
        }
        if (VariableUtil.isString(object)){
            if (VariableUtil.tryDouble((String) object)){
                value = Double.parseDouble((String) object);
            }
        }
        return value;
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