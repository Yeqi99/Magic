package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.MethodUtil;

public class ExecuteSpellFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext= MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();

        return null;
    }

    @Override
    public String getName() {
        return "executeSpell";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
