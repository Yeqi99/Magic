package cn.origincraft.magic.function.system.variable.get;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ListResult;
import cn.origincraft.magic.function.results.SetResult;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.ObjectResult;

import java.util.ArrayList;
import java.util.List;

public class ContainerRandomGetFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size()<1) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "ContainerGet function requires at least one argument.");
        }
        FunctionResult container = args.get(0);
        if (container instanceof ListResult){
            List<Object> list=((ListResult) container).getList();
            int i=(int)(Math.random()*list.size());
            Object o=list.get(i);
            return new ObjectResult(o);
        }else if (container instanceof SetResult){
            List<Object> list=new ArrayList<>(((SetResult) container).getSet());
            int i=(int)(Math.random()*list.size());
            Object o=list.get(i);
            return new ObjectResult(o);
        }
        return null;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "containerRandomGet";
    }
}
