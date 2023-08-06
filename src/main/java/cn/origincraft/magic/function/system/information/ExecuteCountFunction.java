package cn.origincraft.magic.function.system.information;

import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;

public class ExecuteCountFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        FunctionResult.IntResult intResult=new FunctionResult.IntResult(spellContext.getExecuteCount());
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
