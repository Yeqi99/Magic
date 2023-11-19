package cn.origincraft.magic.function.system.variable.container;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id) {
            case "A": {
                Set<?> set = ((SetResult) args.get(0)).getSet();
                return new ListResult(new ArrayList<>(set));
            }
            case "B": {
                Object object = args.get(0).getObject();
                if (!(object instanceof List<?>)) {
                    return new ErrorResult("TYPE_ERROR", "Object convert to list failed");
                }
                return new ListResult((List<?>) object);
            }
            case "C": {
                List<?> list = (List<?>) args.get(0).getObject();
                NumberResult numberResult = (NumberResult) args.get(1);
                return new ObjectResult(list.get(numberResult.toInteger()));
            }
            case "D":{
                Map<?,?> map= (Map<?, ?>) args.get(0).getObject();
                List<Object> list=new ArrayList<>();
                list.add(new ArrayList<>(map.keySet()));
                list.add(new ArrayList<>(map.values()));
                return new ListResult(list);
            }
            case "E": {
                List<Object> resultList = new ArrayList<>();
                for (FunctionResult arg : args) {
                    resultList.add(arg.getObject());
                }
                return new ListResult(resultList);
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("Set")
                        .addInfo("set")
                        .addInfo("Create a list from set.")
                        .setResultType("List")
        );
        argsSettings.add(
                new ArgsSetting("B")
                        .addArgType(".")
                        .addInfo("object")
                        .addInfo("Convert object to list")
                        .setResultType("List")
        );
        argsSettings.add(
                new ArgsSetting("C")
                        .addArgType("List").addArgType("Number")
                        .addInfo("list index")
                        .setResultType("Object")
        );
        argsSettings.add(
                new ArgsSetting("D")
                        .addArgType("Map")
                        .addInfo("map")
                        .addInfo("Convert map to list")
                        .addInfo("list(mapList number(0)):keyList")
                        .addInfo("list(mapList number(1)):valueList")
                        .addInfo("Note: This function is disorderly")
                        .setResultType("List")
        );
        argsSettings.add(
                new ArgsSetting("E")
                        .addArgType("...")
                        .addInfo("args")
                        .addInfo("Create a list.")
                        .setResultType("List")
        );
        return argsSettings;
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
