package cn.origincraft.magic.interpreter.fastexpression.parameters;


import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;

public class IntegerParameter extends FunctionParameter {
    public IntegerParameter(int integer) {
        setObject(integer);
    }

    public int getInteger() {
        return (int) getObject();
    }

    public void setInteger(int integer) {
        setObject(integer);
    }
}
