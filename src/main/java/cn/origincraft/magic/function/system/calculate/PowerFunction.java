package cn.origincraft.magic.function.system.calculate;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;
import cn.origincraft.magic.utils.VariableUtil;
import dev.rgbmc.expression.functions.CallableFunction;
import dev.rgbmc.expression.functions.FastFunction;
import dev.rgbmc.expression.functions.FunctionParameter;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.parameters.StringParameter;
import java.util.List;

public class PowerFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        MagicManager mm = spellContext.getMagicManager();
        List<Object> list = mm
                .getFastExpression()
                .getFunctionManager()
                .parseParaExpression(para);

        // 第一个参数是基数，第二个参数是指数
        double base = 0;
        double exponent = 0;
        int count = 0;

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

            if (count == 0) {
                base = value;
            } else if (count == 1) {
                exponent = value;
            } else {
                // 只解析前两个参数
                break;
            }

            count++;
        }

        double result = Math.pow(base, exponent);

        if (VariableUtil.hasFractionalPart(result)) {
            spellContext.putExecuteReturn(new FunctionResult.DoubleResult(result));
        } else {
            spellContext.putExecuteReturn(new FunctionResult.IntResult((int) result));
        }

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
        return "power";
    }

    @Override
    public String getType() {
        return "CALCULATE";
    }
}