package cn.origincraft.magic.function.system.variable.container;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ListResult;
import cn.origincraft.magic.function.results.MapResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id) {
            case "A": {
                List<?> keys = ((ListResult) args.get(0)).getList();
                List<?> values = ((ListResult) args.get(1)).getList();
                if (keys.size() != values.size()) {
                    return new ErrorResult("FUNCTION_ERROR", "The number of keys and values is not equal.");
                }
                Map<Object, Object> map = new HashMap<>();
                for (int i = 0; i < keys.size(); i++) {
                    map.put(keys.get(i), values.get(i));
                }
                return new MapResult(map);
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("List").addArgType("List")
                        .addInfo("keys")
                        .setResultType("Map")
        );
        return argsSettings;
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
