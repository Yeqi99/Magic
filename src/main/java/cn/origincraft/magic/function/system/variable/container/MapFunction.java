package cn.origincraft.magic.function.system.variable.container;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ListResult;
import cn.origincraft.magic.function.results.MapResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapFunction extends NormalFunction {

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size() < 2) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "Map function requires at least two arguments.");
        }
        FunctionResult keys = args.get(0);
        FunctionResult values = args.get(1);
        if (keys instanceof ListResult k) {
            if (values instanceof ListResult v) {
                if (k.getList().size() == v.getList().size()) {
                    List<?> keysList = k.getList();
                    List<?> valuesList = v.getList();
                    Map<Object, Object> resultMap = new HashMap<>();

                    for (int i = 0; i < keysList.size(); i++) {
                        resultMap.put(keysList.get(i), valuesList.get(i));
                    }

                    return new MapResult(resultMap);

                }
            }
        }
        return new ErrorResult("MAP_FUNCTION_ARGS_ERROR", "Map function args error.");
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "map";
    }
}
