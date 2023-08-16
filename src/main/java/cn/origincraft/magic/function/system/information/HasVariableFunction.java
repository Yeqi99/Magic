package cn.origincraft.magic.function.system.information;

import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;
import dev.rgbmc.expression.functions.FastFunction;
import dev.rgbmc.expression.functions.FunctionParameter;
import dev.rgbmc.expression.functions.FunctionResult;

public class HasVariableFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String stringResult=spellContext.getExecuteParameter();
        FunctionResult.BooleanResult booleanResult=new FunctionResult.BooleanResult(spellContext.getVariableMap().containsKey(stringResult));
        spellContext.putExecuteReturn(booleanResult);
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "hasVariable";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
