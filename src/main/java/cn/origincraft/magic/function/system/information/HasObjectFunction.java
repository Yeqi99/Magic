package cn.origincraft.magic.function.system.information;

import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;
import dev.rgbmc.expression.functions.FastFunction;
import dev.rgbmc.expression.functions.FunctionParameter;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.BooleanResult;

public class HasObjectFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String stringResult=spellContext.getExecuteParameter();
        BooleanResult booleanResult = new BooleanResult(spellContext.getObjectMap().containsKey(stringResult));
        spellContext.putExecuteReturn(booleanResult);
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "hasObject";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
