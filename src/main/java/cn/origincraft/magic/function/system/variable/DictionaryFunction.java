package cn.origincraft.magic.function.system.variable;

import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;
import dev.rgbmc.expression.functions.CallableFunction;
import dev.rgbmc.expression.functions.FastFunction;
import dev.rgbmc.expression.functions.FunctionParameter;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.managers.FunctionManager;
import dev.rgbmc.expression.parameters.StringParameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryFunction implements FastFunction {
    /**
     * 根据参数生成一个Map<String, Object>存入SpellContext中
     * 生成的Map<String, Object>的长度为参数个数
     * 生成的Map<String, Object>的每个元素为参数的值
     * 注意：参数的值为Object类型，可能为任意类型
     * 注意：参数可能是另一个方法的嵌套
     * @param parameter 参数
     * @return 返回值是SpellContextResult类型，其中包含了SpellContext 并且SpellContext中的executeReturn是一个Map<String, Object>
     */
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        FunctionManager fManager = spellContext
                .getMagicManager()
                .getFastExpression()
                .getFunctionManager();
        List<Object> os = fManager.parseParaExpression(para);

        Map<String, Object> resultMap = new HashMap<>();

        for (int i = 0; i < os.size(); i += 2) {
            Object key = os.get(i);

            if (i + 1 >= os.size()) {
                break; // Ignore key without value
            }

            Object value = os.get(i + 1);

            if (MethodUtil.isFunction(value)) {
                CallableFunction callableFunction = (CallableFunction) value;
                StringParameter stringParameter = (StringParameter) callableFunction.getParameter();
                spellContext.putExecuteParameter(stringParameter.getString());
                SpellContextParameter spellContextParameter = new SpellContextParameter(spellContext);
                SpellContextResult spellContextResult = (SpellContextResult) callableFunction.getFunction().call(spellContextParameter);
                spellContext = spellContextResult.getSpellContext();
                FunctionResult functionResult = spellContextResult.getSpellContext().getExecuteReturn();
                Object resultValue = extractFunctionResultValue(functionResult);
                resultMap.put(key.toString(), resultValue);
            } else if (spellContext.getVariableMap().containsKey(value.toString())) {
                Object v = spellContext.getVariableMap().get(value.toString());
                resultMap.put(key.toString(), v);
            } else {
                resultMap.put(key.toString(), value);
            }
        }

        spellContext.putExecuteReturn(new FunctionResult.MapResult(resultMap));
        return new SpellContextResult(spellContext);
    }
    /**
     * 从FunctionResult中提取值
     * @param functionResult FunctionResult
     * @return 返回值
     */
    private Object extractFunctionResultValue(FunctionResult functionResult) {
        if (functionResult instanceof FunctionResult.ListResult) {
            return ((FunctionResult.ListResult) functionResult).getList();
        } else if (functionResult instanceof FunctionResult.ObjectResult) {
            return ((FunctionResult.ObjectResult) functionResult).getObject();
        } else if (functionResult instanceof FunctionResult.StringResult) {
            return ((FunctionResult.StringResult) functionResult).getString();
        } else if (functionResult instanceof FunctionResult.IntResult) {
            return ((FunctionResult.IntResult) functionResult).getInt();
        } else if (functionResult instanceof FunctionResult.DoubleResult) {
            return ((FunctionResult.DoubleResult) functionResult).getDouble();
        } else if (functionResult instanceof FunctionResult.BooleanResult) {
            return ((FunctionResult.BooleanResult) functionResult).getBoolean();
        } else if (functionResult instanceof FunctionResult.DefaultResult v) {
            if (v.getStatus() == FunctionResult.Status.SUCCESS) {
                return true;
            } else if (v.getStatus() == FunctionResult.Status.FAILURE) {
                return false;
            } else {
                return null;
            }
        }
        return null;
    }
    @Override
    public String getName() {
        return "dictionary";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
