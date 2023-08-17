package cn.origincraft.magic.function.system.variable.has;

import cn.origincraft.magic.function.HasVariableFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.BooleanResult;
import dev.rgbmc.expression.results.StringResult;

import java.util.List;

public class ObjectHasFunction extends HasVariableFunction{
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()){
            return new ErrorResult("OBJECT_HAS_FUNCTION_ARGS_ERROR","VariableHas don't have enough args.");
        }
        FunctionResult functionResult=args.get(0);
        if (functionResult instanceof StringResult v) {
            return new BooleanResult(spellContext.getContextMap().hasObject(v.getString()));
        }
        return new ErrorResult("UNKNOWN_ARGUMENT_TYPE","Unsupported argument type.");
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "ObjectGet";
    }
}
