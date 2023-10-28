package cn.origincraft.magic.function.system.variable.get;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;
import java.util.Map;

public class MapGetFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size()<2) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "ContainerGet function requires at least two arguments.");
        }
        FunctionResult container = args.get(0);
        FunctionResult key = args.get(1);
        if (!(container instanceof MapResult)){
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
        Map<?,?> map=((MapResult) container).getMap();
        Object keyO=null;
        if (key instanceof StringResult){
            keyO=((StringResult) key).getString();
        }else {
            keyO= ((ObjectResult)key).getObject();
        }
        if (map.containsKey(keyO)){
            return new ObjectResult(map.get(keyO));
        }else {
            return new NullResult();
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "mapGet";
    }
}
