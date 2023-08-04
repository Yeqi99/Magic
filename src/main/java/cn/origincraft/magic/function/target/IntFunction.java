package cn.origincraft.magic.function.target;

import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.VariableUtil;

public class IntFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContextParameter spellContextParameter= (SpellContextParameter) parameter;
        SpellContext spellContext=spellContextParameter.getSpellContext();
        String stringParameter=spellContext.getExecuteParameter();
        String[] p=stringParameter.split(" ");
        if (p.length==0){
            return new FunctionResult.IntResult(0);
        }
        if (p.length==1){
            if (VariableUtil.isInt(p[0])){
                return new FunctionResult.IntResult(Integer.parseInt(p[0]));
            }else {
                return new FunctionResult.IntResult(0);
            }
        }
        if (p.length==2){
            int result=0;
            if (VariableUtil.isInt(p[0])){
                result = Integer.parseInt(p[0]);
            }

        }
        return new SpellContextResult(spellContext);
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
