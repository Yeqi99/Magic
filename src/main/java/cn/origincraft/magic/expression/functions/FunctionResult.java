package cn.origincraft.magic.expression.functions;

public class FunctionResult implements MagicResult {
    @Override
    public Object getObject() {
        return this;
    }

    @Override
    public String getName() {
        return "FunctionResult";
    }
}
