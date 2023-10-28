package cn.origincraft.magic.function.system.info;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.IntegerResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class MagicWordsIndexFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        return new IntegerResult(spellContext.getExecuteIndex());
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "magicWordsIndex";
    }
}
