package cn.origincraft.magic.function.system.out;

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

import java.util.List;

public class PrintFunction implements FastFunction {

    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext= MethodUtil.getSpellContext(parameter);
        String para=spellContext.getExecuteParameter();
        MagicManager mm=spellContext.getMagicManager();
        List<Object> list=mm
                .getFastExpression()
                .getFunctionManager()
                .parseParaExpression(para);
        StringBuilder s= new StringBuilder();
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
                    s.append(v.getDouble());
                }
                // Int型
                if (functionResult instanceof FunctionResult.IntResult v){
                    s.append(v.getInt());
                }
                // String型
                if (functionResult instanceof FunctionResult.StringResult v){
                    s.append(v.getString());
                }
                // Object型
                if (functionResult instanceof FunctionResult.ObjectResult v){
                    s.append(v.getObject());
                }
                // Boolean型
                if (functionResult instanceof FunctionResult.BooleanResult v){
                    s.append(v.getBoolean());
                }
            }else {
                String str= (String) o;
                s.append(spellContext.getVariableMap().getOrDefault(str, str));
            }
        }

        if (s.length()>0){
            System.out.print(s);
        }
        spellContext.putExecuteReturn(new FunctionResult.DefaultResult(FunctionResult.Status.SUCCESS));
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "print";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
