package cn.origincraft.magic.function.results;


import cn.origincraft.magic.expression.functions.FunctionResult;

public class ObjectResult extends FunctionResult{
    private final Object object;
    public ObjectResult(){
        object=null;
    }

    public ObjectResult(Object object) {
        this.object = object;
    }
    public ObjectResult(FunctionResult functionResult){
        this.object = functionResult.getObject();
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
