package cn.origincraft.magic.function.system.variable.container;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.*;
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
            case "B": {
                Object object=args.get(0).getObject();
                if (!(object instanceof Map<?,?>)){
                    return new ErrorResult("TYPE_ERROR","Object convert to map failed");
                }
                return new MapResult((Map<?, ?>) object);
            }
            case "C":{
                Map<?,?> map= (Map<?, ?>) args.get(0).getObject();
                Object key = args.get(1).getObject();
                return new ObjectResult(map.get(key));
            }
            case "D":{
                MapResult mapResult= (MapResult) args.get(0);
                Map<Object,Object> map=mapResult.getObjectMap();
                Object key = args.get(1).getObject();
                Object value = args.get(2).getObject();
                map.put(key,value);
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
        argsSettings.add(
                new ArgsSetting("B")
                        .addArgType(".")
                        .addInfo("object")
                        .addInfo("Convert object to list")
                        .setResultType("Map")
        );
        argsSettings.add(
                new ArgsSetting("C")
                        .addArgType("Map").addArgType("Object")
                        .addInfo("map key")
                        .addInfo("Get value by key")
                        .setResultType("Object")
        );
        argsSettings.add(
                new ArgsSetting("D")
                        .addArgType("Map").addArgType("Object").addArgType("Object")
                        .addInfo("map key value")
                        .addInfo("Put value to map by key")
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
