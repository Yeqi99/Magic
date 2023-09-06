package cn.origincraft.magic.function.system.variable.get;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ListResult;
import cn.origincraft.magic.function.results.MapResult;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;

import java.util.ArrayList;
import java.util.List;

public class MapGetValueListFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size()<1) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "ContainerGet function requires at least one argument.");
        }
        FunctionResult container = args.get(0);
        if (container instanceof MapResult){
            List<Object> list=new ArrayList<>(((MapResult) container).getMap().values());
            return new ListResult(list);
        }else{
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "mapGetValueList";
    }
}
