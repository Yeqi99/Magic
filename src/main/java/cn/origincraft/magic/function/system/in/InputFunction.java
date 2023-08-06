package cn.origincraft.magic.function.system.in;

import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;

public class InputFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        return null;
    }

    @Override
    public String getName() {
        return "input";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
