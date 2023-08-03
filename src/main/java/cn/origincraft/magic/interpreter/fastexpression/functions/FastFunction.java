package cn.origincraft.magic.interpreter.fastexpression.functions;

public interface FastFunction {
    FunctionResult call(FunctionParameter parameter);

    String getName();
    String getType();
}
