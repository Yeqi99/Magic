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
import java.util.*;

//加法运算
public class AddFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext= MethodUtil.getSpellContext(parameter);
        String para=spellContext.getExecuteParameter();
        MagicManager mm=spellContext.getMagicManager();
        List<Object> list=mm
                .getFastExpression()
                .getFunctionManager()
                .parseParaExpression(para);
        double result = 0;
        List<Object> maybeResult=new ArrayList<>();
        Map<String,Object> maybeMapResult=new HashMap<>();
        Set<Object> maybeSetResult=new HashSet<>();
        for (Object o : list) {
            if (MethodUtil.isFunction(o)){
                CallableFunction function= (CallableFunction) o;
                StringParameter stringParameter=
                        (StringParameter)function.getParameter();
                spellContext.putExecuteParameter(stringParameter.getString());
                SpellContextResult spellContextResult =
                        (SpellContextResult) function
                                .getFunction()
                                .call(
                                        new SpellContextParameter(spellContext)
                                );
                spellContext = spellContextResult.getSpellContext();
                FunctionResult functionResult = spellContext.getExecuteReturn();
                // 判断值类型处理
                // Double型
                if (functionResult instanceof FunctionResult.DoubleResult v){
                    result+=v.getDouble();
                }
                // Int型
                if (functionResult instanceof FunctionResult.IntResult v){
                    result+=v.getInt();
                }
                // String型
                if (functionResult instanceof FunctionResult.StringResult v){
                    if (VariableUtil.tryDouble(v.getString())){
                        result+=Double.parseDouble(v.getString());
                    }
                }
                // List型
                if (functionResult instanceof FunctionResult.ListResult v){
                    maybeResult.addAll(v.getList());
                }
                // Object型
                if (functionResult instanceof FunctionResult.ObjectResult v){
                    if (VariableUtil.isDouble(v.getObject())){
                        result += (double)v.getObject();
                    }
                    if (VariableUtil.isInt(v.getObject())){
                        result += (int)v.getObject();
                    }
                }
                // Boolean型
                if (functionResult instanceof FunctionResult.BooleanResult v){
                    if (v.getBoolean()){
                        result+=1;
                    }
                }
                // Map型
                if (functionResult instanceof FunctionResult.MapResult v){
                    maybeMapResult.putAll(v.getMap());
                }
                // Set型
                if (functionResult instanceof FunctionResult.SetResult v){
                    maybeSetResult.addAll(v.getSet());
                }
            }else {
                String value= (String) o;
                if (spellContext
                        .getVariableMap()
                        .containsKey(value)) {
                    Object v = spellContext.getVariableMap().get(value);
                    if (VariableUtil.isDouble(v)) {
                        result+=(double) v;
                    }
                    if (VariableUtil.isInt(v)){
                        result+=(int) v;
                    }
                    if (VariableUtil.isString(v)){
                        if (VariableUtil.tryDouble((String) v)){
                            result+=Double.parseDouble((String)v);
                        }
                    }
                    if (v instanceof List){
                        maybeResult.addAll((List<?>) v);
                    }
                    if (VariableUtil.isBoolean(v)){
                        if ((boolean) v){
                            result+=1;
                        }
                    }
                    if (v instanceof Map){
                        maybeMapResult.putAll((Map<? extends String, ?>) v);
                    }
                    if (v instanceof Set){
                        maybeSetResult.addAll((Set<?>) v);
                    }
                } else {
                    if (VariableUtil.tryDouble(value)) {
                        result+=Double.parseDouble(value);
                    }
                }
            }
        }
        if (!maybeMapResult.isEmpty()) {
            spellContext.putExecuteReturn(new FunctionResult.MapResult(maybeMapResult));
        } else if (!maybeSetResult.isEmpty() && !maybeResult.isEmpty()) {
            Set<Object> combinedSet = new HashSet<>(maybeSetResult);
            combinedSet.addAll(maybeResult);
            spellContext.putExecuteReturn(new FunctionResult.SetResult(combinedSet));
        } else if (!maybeSetResult.isEmpty()) {
            spellContext.putExecuteReturn(new FunctionResult.SetResult(maybeSetResult));
        } else if (!maybeResult.isEmpty()) {
            spellContext.putExecuteReturn(new FunctionResult.ListResult(maybeResult));
        } else if (VariableUtil.hasFractionalPart(result)) {
            spellContext.putExecuteReturn(new FunctionResult.DoubleResult(result));
        } else {
            spellContext.putExecuteReturn(new FunctionResult.IntResult((int) result));
        }
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getType() {
        return "CALCULATE";
    }
}
