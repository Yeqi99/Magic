package cn.origincraft.magic.function.system.variable.get;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ListResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtil;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.ObjectResult;
import dev.rgbmc.expression.results.StringResult;

import java.util.List;

public class ListGetFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size()<2) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "ContainerGet function requires at least two arguments.");
        }
        FunctionResult container = args.get(0);
        FunctionResult key = args.get(1);
        String k="";
        if (key instanceof StringResult){
            k=((StringResult) key).getString();
        }else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
        if (container instanceof ListResult){
            List<Object> list=((ListResult) container).getList();
            if (!VariableUtil.tryInt(k)){
                return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
            }
            int i=Integer.parseInt(k);
            if (i<list.size()){
                Object o=list.get(i);
                return new ObjectResult(o);
            }else {
                return new NullResult();
            }
        }else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "containerGet";
    }
}
