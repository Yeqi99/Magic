package cn.origincraft.magic.interpreter.fastexpression.parameters;


import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;

public class BooleanParameter extends FunctionParameter {
    public BooleanParameter(boolean bool) {
        setObject(bool);
    }

    public boolean getBoolean() {
        return (boolean) getObject();
    }

    public void setBoolean(boolean bool) {
        setObject(bool);
    }
}
