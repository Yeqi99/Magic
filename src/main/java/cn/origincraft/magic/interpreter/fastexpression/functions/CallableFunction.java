package cn.origincraft.magic.interpreter.fastexpression.functions;



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
}
