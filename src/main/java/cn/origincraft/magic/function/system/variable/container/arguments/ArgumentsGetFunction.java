package cn.origincraft.magic.function.system.variable.container.arguments;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ArgumentsResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.IntegerResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class ArgumentsGetFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size()<2) {
            return new ErrorResult("ARGUMENTS_GET_FUNCTION_ARGS_ERROR", "ArgumentsGet don't have enough args.");
        }
        FunctionResult argsResult=args.get(0);
        FunctionResult indexResult=args.get(1);
        if (argsResult instanceof ArgumentsResult args1) {
            if (indexResult instanceof IntegerResult index) {
                if (index.getInteger()<0||index.getInteger()>=args1.getArgs().size()) {
                    return new NullResult();
                }
                return args1.getArgs().get(index.getInteger());
            }else {
                return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
            }
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
        return "argumentsGet";
    }
}
