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
