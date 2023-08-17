package cn.origincraft.magic.function.system.variable.define;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.BooleanResult;
import dev.rgbmc.expression.results.DoubleResult;
import dev.rgbmc.expression.results.IntegerResult;
import dev.rgbmc.expression.results.StringResult;

import java.util.List;

public class VariableDefineFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size() < 2) {
            return new ErrorResult("VARIABLE_DEFINE_FUNCTION_ARGS_ERROR", "Variable define don't have enough args.");
        }
        String varName="";
        if (args.get(0) instanceof StringResult name) {
            varName=name.getString();
        }else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
        if (args.get(1) instanceof StringResult v) {
            spellContext.getContextMap().putVariable(varName, v.getString());
        }else if (args.get(1) instanceof DoubleResult v) {
            spellContext.getContextMap().putVariable(varName, v.getDouble());
        }else if (args.get(1) instanceof BooleanResult v) {
            spellContext.getContextMap().putVariable(varName, v.getBoolean());
        }else if (args.get(1) instanceof IntegerResult v) {
            spellContext.getContextMap().putVariable(varName, v.getInteger());
        }else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
        return new NullResult();

    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "define";
    }
}
