package cn.origincraft.magic.function.system.information;

import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;

public class HasObjectFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String stringResult=spellContext.getExecuteParameter();
        FunctionResult.BooleanResult booleanResult=new FunctionResult.BooleanResult(spellContext.getObjectMap().containsKey(stringResult));
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
