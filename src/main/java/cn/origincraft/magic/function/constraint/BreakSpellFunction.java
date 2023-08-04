package cn.origincraft.magic.function.constraint;

import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;

public class BreakSpellFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext= MethodUtil.getSpellContext(parameter);
        spellContext.putExecuteBreak(true);
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "BreakSpell";
    }

    @Override
    public String getType() {
        return "CONSTRAINT";
    }
}
