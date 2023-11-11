package cn.origincraft.magic.expression.functions;


import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.expression.parameters.StringParameter;

import java.util.List;

public class CallableFunction {
    private final FastFunction function;
    private FunctionParameter parameter;

    public CallableFunction(FastFunction function, FunctionParameter parameter) {
        this.function = function;
        this.parameter = parameter;
    }

    public FastFunction getFunction() {
        return function;
    }

    public FunctionParameter getParameter() {
        return parameter;
    }

    public void setParameter(FunctionParameter parameter) {
        this.parameter = parameter;
    }

    public FunctionResult callFunction() {
        return function.call(parameter);
    }

    public List<String> getAliases(MagicManager magicManager) {
        return magicManager.getFastExpression().getAliasesManager().getAliases().get(function.getName());
    }
    public String toString() {
        return function.getName() + "(" + ((StringParameter) getParameter()).getString() + ")";
    }
}
