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
    public String toString() {
        return getObject().toString();
    }

    public Number toNumber(Number defaultValue){
        if (!(getObject() instanceof Number)) {
            return defaultValue;
        }
        return (Number) getObject();
    }

    public Boolean toBoolean(Boolean defaultValue){
        if (!(getObject() instanceof Boolean)) {
            return defaultValue;
        }
        return (Boolean) getObject();
    }
}
