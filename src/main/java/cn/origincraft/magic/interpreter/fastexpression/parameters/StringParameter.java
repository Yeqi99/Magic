package cn.origincraft.magic.interpreter.fastexpression.parameters;


import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;

public class StringParameter extends FunctionParameter {
    public StringParameter(String parameter) {
        setObject(parameter);
    }

    public String getString() {
        return (String) getObject();
    }

    public void setString(String string) {
        setObject(string);
    }
}
