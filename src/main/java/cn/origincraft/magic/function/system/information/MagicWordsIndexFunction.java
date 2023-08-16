package cn.origincraft.magic.function.system.information;

import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;
import dev.rgbmc.expression.functions.FastFunction;
import dev.rgbmc.expression.functions.FunctionParameter;
import dev.rgbmc.expression.functions.FunctionResult;

public class MagicWordsIndexFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        FunctionResult.IntResult intResult=new FunctionResult.IntResult(spellContext.getExecuteIndex());
        spellContext.putExecuteReturn(intResult);
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "magicWordsIndex";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
