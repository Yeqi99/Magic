package cn.origincraft.magic.function.system.variable.put;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.ContextMap;
import cn.origincraft.magic.object.NormalContext;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PutFunction extends ArgsFunction {
    @Override
    public String getName() {
        return "put";
    }
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id=argsSetting.getId();
        switch (id){
            case "A":{
                List<FunctionResult> list= (List<FunctionResult>) args.get(0).getObject();
                list.add(args.get(1));
                return new ArgumentsResult(list);
            }
            case "B":{
                List<?> list= (List<?>) args.get(0).getObject();
                List<Object> newList=new ArrayList<>(list);
                newList.add(args.get(1).getObject());
                return new ListResult(newList);
            }
            case "C":{
                List<?> list= (List<?>) args.get(0).getObject();
                List<?> list1= (List<?>) args.get(1).getObject();
                List<Object> newList=new ArrayList<>(list);
                newList.addAll(list1);
                return new ListResult(newList);
            }
            case "D":{
                List<FunctionResult> list= (List<FunctionResult>) args.get(0).getObject();
                List<FunctionResult> list1= (List<FunctionResult>) args.get(1).getObject();
                List<FunctionResult> newList=new ArrayList<>(list);
                newList.addAll(list1);
                return new ArgumentsResult(newList);
            }
            case "E":{
                Map<?,?> map= (Map<?, ?>) args.get(0).getObject();
                Map<?,?> map1= (Map<?, ?>) args.get(1).getObject();
                Map<Object,Object> newMap= new HashMap<>();
                newMap.putAll(map);
                newMap.putAll(map1);
                return new MapResult(newMap);
            }
            case "F":{
                Map<?,?> map= (Map<?, ?>) args.get(0).getObject();
                Object key=args.get(1).getObject();
                Object value=args.get(2).getObject();
                Map<Object, Object> newMap = new HashMap<>(map);
                newMap.put(key,value);
                return new MapResult(newMap);
            }
            case "G":{
                ContextMap contextMap= (ContextMap) args.get(0).getObject();
                String type=args.get(1).toString();
                String key=args.get(2).toString();
                Object value=args.get(3).getObject();
                if (type.toLowerCase().startsWith("var")) {
                    contextMap.putVariable(key, value);
                }else {
                    contextMap.putObject(key, value);
                }
                return new ContextMapResult(contextMap);
            }
            case "H":{
                ContextMap contextMap= (ContextMap) args.get(0).getObject();
                ContextMap contextMap1= (ContextMap) args.get(1).getObject();
                ContextMap newContextMap=new NormalContext();
                for (String variableName : contextMap.getVariableNames()) {
                    newContextMap.putVariable(variableName,contextMap.getVariable(variableName));
                }
                for (String variableName : contextMap1.getVariableNames()) {
                    newContextMap.putVariable(variableName,contextMap1.getVariable(variableName));
                }
                for (String objectName : contextMap.getObjectNames()) {
                    newContextMap.putObject(objectName,contextMap.getObject(objectName));
                }
                for (String objectName : contextMap1.getObjectNames()) {
                    newContextMap.putObject(objectName,contextMap1.getObject(objectName));
                }
                return new ContextMapResult(newContextMap);
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings=new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("Arguments").addArgType(".")
                        .addInfo("arguments")
                        .addInfo("Add a element to list")
                        .setResultType("Arguments")
        );
        argsSettings.add(
                new ArgsSetting("B")
                        .addArgType("List").addArgType(".")
                        .addInfo("list")
                        .addInfo("Add a element to list")
                        .setResultType("List")
        );
        argsSettings.add(
                new ArgsSetting("C")
                        .addArgType("List").addArgType("List")
                        .addInfo("list list")
                        .addInfo("Add a element to list")
                        .setResultType("List")
        );
        argsSettings.add(
                new ArgsSetting("D")
                        .addArgType("Arguments").addArgType("Arguments")
                        .addInfo("arguments arguments")
                        .addInfo("Add a element to list")
                        .setResultType("Arguments")
        );
        argsSettings.add(
                new ArgsSetting("E")
                        .addArgType("Map").addArgType("Map")
                        .addInfo("map map")
                        .addInfo("Add a element to map")
                        .setResultType("Map")
        );
        argsSettings.add(
                new ArgsSetting("F")
                        .addArgType("Map").addArgType(".").addArgType(".")
                        .addInfo("map object object")
                        .addInfo("Add a element to map")
                        .setResultType("Map")
        );
        argsSettings.add(
                new ArgsSetting("G")
                        .addArgType("ContextMap").addArgType("String").addArgType("String").addArgType(".")
                        .addInfo("contextMap string object object")
                        .addInfo("Add a element to contextMap")
                        .setResultType("ContextMap")
        );
        argsSettings.add(
                new ArgsSetting("H")
                        .addArgType("ContextMap").addArgType("ContextMap")
                        .addInfo("contextMap contextMap")
                        .addInfo("Merge contextMap")
                        .setResultType("ContextMap")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
