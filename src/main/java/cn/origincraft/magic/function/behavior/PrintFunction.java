package cn.origincraft.magic.function.behavior;

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
        String s="";
        List<CallableFunction> list=mm
                .getFastExpression()
                .getFunctionManager()
                .parseExpression(para);
        if (list.size()>0){
            StringParameter stringParameter=
                    (StringParameter) list.get(0).getParameter();
            spellContext.putExecuteParameter(stringParameter.getString());
            FunctionResult functionResult= list.get(0).getFunction().call(new SpellContextParameter(spellContext));
            if (functionResult instanceof FunctionResult.ObjectResult){
                s = String.valueOf(((FunctionResult.ObjectResult) functionResult).getObject());
            }
            if (functionResult instanceof FunctionResult.IntResult){
                s = String.valueOf(((FunctionResult.IntResult)functionResult).getInt());
            }
            if (functionResult instanceof FunctionResult.DoubleResult){
                s = String.valueOf(((FunctionResult.DoubleResult)functionResult).getDouble());
            }
            System.out.print(s);
        }else {
            if(spellContext.getVariableMap().containsKey(para)){
                System.out.print(spellContext.getVariableMap().get(para));
            }else {
                System.out.print(para);
            }
            return new SpellContextResult(spellContext);
        }
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "print";
    }

    @Override
    public String getType() {
        return "BEHAVIOR";
    }
}
