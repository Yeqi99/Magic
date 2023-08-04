package cn.origincraft.magic.function.calculate;

import cn.origincraft.magic.interpreter.fastexpression.functions.CallableFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.interpreter.fastexpression.parameters.StringParameter;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.utils.MethodUtil;
import cn.origincraft.magic.utils.VariableUtil;

import java.util.List;

public class AddFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext= MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        String[] pares=para.split(" ");
        double result=0;
        for (String s : pares) {
            List<CallableFunction> list= spellContext
                    .getMagicManager()
                    .getFastExpression()
                    .getFunctionManager()
                    .parseExpression(s);
            if (list.size()>0){
                StringParameter sPara=
                        (StringParameter) list.get(0).getParameter();
                spellContext.putExecuteParameter(sPara.getString());
                FunctionResult functionResult= list.get(0).getFunction().call(new SpellContextParameter(spellContext));
                if (functionResult instanceof FunctionResult.DoubleResult){
                    result+=((FunctionResult.DoubleResult) functionResult).getDouble();
                }
                if (functionResult instanceof FunctionResult.IntResult){
                    result+=((FunctionResult.IntResult) functionResult).getInt();
                }
            }else {
                if(spellContext.getVariableMap().containsKey(s)){
                    result += (double) spellContext.getVariableMap().get(s);
                }else {
                    if (VariableUtil.isDouble(s)){
                        result += Double.parseDouble(s);
                    }
                }
            }
        }
        if (VariableUtil.hasFractionalPart(result)){
            return new FunctionResult.ObjectResult(result);
        }else {
            return new FunctionResult.ObjectResult((int)result);
        }
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
