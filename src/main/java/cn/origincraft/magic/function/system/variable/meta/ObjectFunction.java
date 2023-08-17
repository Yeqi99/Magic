package cn.origincraft.magic.function.system.variable.meta;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.*;

import java.util.List;

public class ObjectFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("INT_FUNCTION_ARGS_ERROR", "Object don't have enough args.");
        }
        FunctionResult functionResult = args.get(0);
        if (functionResult instanceof IntegerResult v) {
            return new ObjectResult(v.getInteger());
        }else if (functionResult instanceof StringResult v) {
            return new ObjectResult(v.getString());
        }else if (functionResult instanceof DoubleResult v) {
            return new ObjectResult(v.getDouble());
        }else if (functionResult instanceof BooleanResult v) {
            return new ObjectResult(v.getBoolean());
        }else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "object";
    }
}
