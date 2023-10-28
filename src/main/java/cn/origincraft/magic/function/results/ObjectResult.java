package cn.origincraft.magic.function.results;


import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.expression.functions.MagicResult;

public class ObjectResult extends FunctionResult implements MagicResult {
    private final Object object;

    public ObjectResult(Object object) {
        this.object = object;
    }

    @Override
    public Object getObject() {
        return object;
    }

    @Override
    public String getName() {
        return "Object";
    }
}
