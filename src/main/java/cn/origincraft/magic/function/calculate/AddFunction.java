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
            }else {
                String value= (String) o;
                if (spellContext
                        .getVariableMap()
                        .containsKey((String) value)) {
                    Object v = spellContext.getVariableMap().get((String) value);
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
                } else {
                    if (VariableUtil.tryDouble(value)) {
                        result+=Double.parseDouble(value);
                    }
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

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getType() {
        return "CALCULATE";
    }
}
