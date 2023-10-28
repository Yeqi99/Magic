package cn.origincraft.magic.function.system.control.execute;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class MagicWordsBreakFunction extends NormalFunction {

    @Override
    public String getName() {
        return "magicWordsBreak";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        spellContext.putExecuteIndexAllow(spellContext.getExecuteIndex(), false);
        return new NullResult();
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
