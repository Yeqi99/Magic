package cn.origincraft.magic.function.system.variable.meta;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.OnlyStringFunction;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class OriginStringFunction extends OnlyStringFunction {
    @Override
    public String getName() {
        return "originString";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, String args) {
        return new StringResult(args);
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public List<String> getUsage() {
        return null;
    }
}
