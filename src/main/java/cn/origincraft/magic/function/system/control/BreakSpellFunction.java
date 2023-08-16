package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;
import dev.rgbmc.expression.functions.FastFunction;
import dev.rgbmc.expression.functions.FunctionParameter;
import dev.rgbmc.expression.functions.FunctionResult;

public class BreakSpellFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext= MethodUtil.getSpellContext(parameter);
        spellContext.putExecuteBreak(true);
        spellContext.putExecuteReturn(new FunctionResult.DefaultResult(FunctionResult.Status.SUCCESS));
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "breakSpell";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
