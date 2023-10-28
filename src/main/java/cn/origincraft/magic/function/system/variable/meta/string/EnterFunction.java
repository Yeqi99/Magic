package cn.origincraft.magic.function.system.variable.meta.string;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.IntegerResult;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.MethodUtil;
import cn.origincraft.magic.utils.VariableUtil;

import java.util.List;

public class EnterFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new StringResult("\n");
        }
        FunctionResult functionResult = args.get(0);
        if (functionResult instanceof StringResult v) {
            if (VariableUtil.tryInt(v.getString())) {
                return new StringResult(MethodUtil.getEnter(Integer.parseInt(v.getString())));
            } else {
                return new StringResult("\n");
            }
        } else if (functionResult instanceof IntegerResult v) {
            return new StringResult(MethodUtil.getEnter(v.getInteger()));
        }
        return new StringResult("\n");
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "enter";
    }
}
