package cn.origincraft.magic.interpreter.fastexpression.functions;

public abstract class FunctionParameter {
    private Object object;

    protected Object getObject() {
        return object;
    }

    protected void setObject(Object object) {
        this.object = object;
    }
}
