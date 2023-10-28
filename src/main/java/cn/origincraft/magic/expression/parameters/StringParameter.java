package cn.origincraft.magic.expression.parameters;

import cn.origincraft.magic.expression.functions.FunctionParameter;

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
