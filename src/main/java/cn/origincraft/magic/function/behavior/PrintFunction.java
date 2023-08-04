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
        List<CallableFunction> list=mm
                .getFastExpression()
                .getFunctionManager()
                .parseExpression(para);
        if (list.size()>0){
            StringParameter stringParameter=
                    (StringParameter) list.get(0).getParameter();
            spellContext.putExecuteParameter(stringParameter.getString());
            FunctionResult.ObjectResult functionResult= (FunctionResult.ObjectResult) list.get(0).getFunction().call(new SpellContextParameter(spellContext));
            String s= String.valueOf(functionResult.getObject());
            System.out.print(s);
        }else {
            if(spellContext.getVariableMap().containsKey(para)){
                System.out.print(spellContext.getVariableMap().get(para));
                return new SpellContextResult(spellContext);
            }
            System.out.print(para);
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
