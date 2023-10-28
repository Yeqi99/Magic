package cn.origincraft.magic.function.system.variable.meta.string;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.IntegerResult;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.MethodUtil;
import cn.origincraft.magic.utils.VariableUtil;

import java.util.List;

public class SpaceFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new StringResult(" ");
        }
        FunctionResult functionResult = args.get(0);
        if (functionResult instanceof StringResult v) {
            if (VariableUtil.tryInt(v.getString())){
                return new StringResult(MethodUtil.getSpace(Integer.parseInt(v.getString())));
            }else {
                return new StringResult(" ");
            }
        }else if (functionResult instanceof IntegerResult v){
            return new StringResult(MethodUtil.getSpace(v.getInteger()));
        }
        return new StringResult(" ");
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "space";
    }
}
