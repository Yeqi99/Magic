package cn.origincraft.magic.expression.functions;

public interface FastFunction {
    FunctionResult call(FunctionParameter parameter);

    String getName();
    String getType();
}
