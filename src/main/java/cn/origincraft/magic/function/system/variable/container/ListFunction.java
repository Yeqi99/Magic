package cn.origincraft.magic.function.system.variable.container;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ListResult;
import cn.origincraft.magic.function.results.ObjectResult;
import cn.origincraft.magic.function.results.SetResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class ListFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("LIST_FUNCTION_ARGS_ERROR", "List don't have enough args.");
        }
        if (args.size() == 1) {
            if (args.get(0) instanceof SetResult v) {
                return new ListResult(new ArrayList<>(v.getSet()));
            }
        }
        List<Object> resultList = new ArrayList<>();
        for (FunctionResult functionResult : args) {
            if (functionResult instanceof ObjectResult v) {
                resultList.add(v.getObject());
            } else {
                resultList.add(functionResult);
            }
        }
        return new ListResult(resultList);
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "list";
    }
}
