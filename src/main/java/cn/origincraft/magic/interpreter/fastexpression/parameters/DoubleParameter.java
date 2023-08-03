package cn.origincraft.magic.interpreter.fastexpression.parameters;


import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;

public class DoubleParameter extends FunctionParameter {
    public DoubleParameter(double value) {
        setObject(value);
    }

    public double getInteger() {
        return (double) getObject();
    }

    public void setInteger(double value) {
        setObject(value);
    }
}
