package cn.origincraft.magic.function.system.variable.has;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.HasVariableFunction;
import cn.origincraft.magic.function.results.BooleanResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class VariableHasFunction extends HasVariableFunction {


    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()){
            return new ErrorResult("VARIABLE_HAS_FUNCTION_ARGS_ERROR","VariableHas don't have enough args.");
        }
        FunctionResult functionResult=args.get(0);
        if (functionResult instanceof StringResult v) {
            return new BooleanResult(spellContext.getContextMap().hasVariable(v.getString()));
        }
        return new ErrorResult("UNKNOWN_ARGUMENT_TYPE","Unsupported argument type.");
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "variableHas";
    }
}
