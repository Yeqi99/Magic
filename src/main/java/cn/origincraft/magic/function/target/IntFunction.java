package cn.origincraft.magic.function.target;

import cn.origincraft.magic.interpreter.fastexpression.functions.CallableFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.interpreter.fastexpression.parameters.StringParameter;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;
import cn.origincraft.magic.utils.VariableUtil;

import java.util.List;


public class IntFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext= MethodUtil.getSpellContext(parameter);
        String para=spellContext.getExecuteParameter();
        String[] s=para.split(" ");
        if (s.length<2){
            return new SpellContextResult(spellContext);
        }
        String variableName=s[0];
        String value=s[1];
        int result=0;
        if (VariableUtil.isInt(value)){
            result=Integer.parseInt(value);
            spellContext.putVariable(variableName,result);
            return new SpellContextResult(spellContext);
        }else{
            List<CallableFunction> list= spellContext
                    .getMagicManager()
                    .getFastExpression()
                    .getFunctionManager()
                    .parseExpression(value);
            if (list.size()>0){
                StringParameter stringParameter=
                        (StringParameter) list.get(0).getParameter();
                FunctionResult functionResult= list.get(0).getFunction().call(stringParameter);
                if (functionResult instanceof FunctionResult.IntResult){
                    result=((FunctionResult.IntResult) functionResult).getInt();
                }
                spellContext.putVariable(variableName,result);
                return new SpellContextResult(spellContext);
            }else {
                if(spellContext.getVariableMap().containsKey(value)){
                    result = (int) spellContext.getVariableMap().get(value);
                }
                spellContext.putVariable(variableName,result);
                return new SpellContextResult(spellContext);
            }
        }
    }

    @Override
    public String getName() {
        return "int";
    }

    @Override
    public String getType() {
        return "TARGET";
    }
}
