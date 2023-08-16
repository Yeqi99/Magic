package cn.origincraft.magic.function.system.information;

import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;
import dev.rgbmc.expression.functions.FastFunction;
import dev.rgbmc.expression.functions.FunctionParameter;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.IntegerResult;

public class ExecuteCountFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
    IntegerResult intResult = new IntegerResult(spellContext.getExecuteCount());
        spellContext.putExecuteReturn(intResult);
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "executeCount";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
