package cn.origincraft.magic.function.system.operations.arithmetic;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class SelfAddFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "SelfAdd function requires at least one argument.");
        }
        if (args.get(0) instanceof IntegerResult) {
            return new IntegerResult(((IntegerResult) args.get(0)).getInteger() + 1);
        } else if (args.get(0) instanceof LongResult) {
            return new LongResult(((LongResult) args.get(0)).getLong() + 1);
        } else if (args.get(0) instanceof BooleanResult) {
            return new BooleanResult(!((BooleanResult) args.get(0)).getBoolean());
        } else if (args.get(0) instanceof StringResult) {
            return new StringResult(((StringResult) args.get(0)).getString() + "1");
        } else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }

    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "selfAdd";
    }
}
