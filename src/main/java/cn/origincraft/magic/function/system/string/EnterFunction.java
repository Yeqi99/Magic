package cn.origincraft.magic.function.system.string;

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

public class EnterFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext= MethodUtil.getSpellContext(parameter);
        String para=spellContext.getExecuteParameter();
        MagicManager mm=spellContext.getMagicManager();
        List<Object> list=mm
                .getFastExpression()
                .getFunctionManager()
                .parseParaExpression(para);
        if (list.size()<1){
            spellContext.putExecuteReturn(new FunctionResult.StringResult(MethodUtil.getEnter(1)));
            return new SpellContextResult(spellContext);
        }
        Object value=list.get(0);
        if (MethodUtil.isFunction(value)){
            CallableFunction function= (CallableFunction) value;
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
            if (functionResult instanceof FunctionResult.IntResult v){
                if (v.getInt()>0){
                    spellContext.putExecuteReturn(new FunctionResult.StringResult(MethodUtil.getEnter(v.getInt())));
                    return new SpellContextResult(spellContext);
                }
            }
        }else {
            // 如果不是嵌套方法 判断是否为变量
            if (spellContext
                    .getVariableMap()
                    .containsKey((String)value)){
                // 提取变量值
                Object v=spellContext.getVariableMap().get((String)value);
                if (VariableUtil.isInt(v)){
                    int num = (int) v;
                    if (num>0){
                        spellContext.putExecuteReturn(new FunctionResult.StringResult(MethodUtil.getEnter(num)));
                        return new SpellContextResult(spellContext);
                    }
                }
            }else {
                if (VariableUtil.tryInt((String) value)){
                    int num = Integer.parseInt((String) value);
                    if (num>0){
                        spellContext.putExecuteReturn(new FunctionResult.StringResult(MethodUtil.getEnter(num)));
                        return new SpellContextResult(spellContext);
                    }
                }
            }
        }
        spellContext.putExecuteReturn(new FunctionResult.StringResult(""));
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "enter";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
