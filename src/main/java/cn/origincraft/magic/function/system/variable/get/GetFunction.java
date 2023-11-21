package cn.origincraft.magic.function.system.variable.get;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class GetFunction extends ArgsFunction {
    @Override
    public String getName() {
        return "get";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id) {
            case "A": {
                String str = args.get(0).toString();
                String index = args.get(1).toString();
                NumberResult numberResult = new NumberResult(index);
                int i = numberResult.toInteger();
                if (i < 0 || i >= str.length()) {
                    return new ErrorResult("OUT_OF_BOUNDS_ERROR", "String identifier out of bounds MAX:" + (str.length() - 1) + ",IN:" + i);
                }
                return new StringResult(str.charAt(i) + "");
            }
            case "B": {
                List<?> list = (List<?>) args.get(0).getObject();
                String index = args.get(1).toString();
                NumberResult numberResult = new NumberResult(index);
                int i = numberResult.toInteger();
                if (i < 0 || i >= list.size()) {
                    return new ErrorResult("OUT_OF_BOUNDS_ERROR", "list identifier out of bounds MAX:" + (list.size() - 1) + ",IN:" + i);
                }
                return new ObjectResult(list.get(i) + "");
            }
            case "C": {
                String str = args.get(0).toString();
                NumberResult numberResult = (NumberResult) args.get(1);
                int i = numberResult.toInteger();
                if (i < 0 || i >= str.length()) {
                    return new ErrorResult("OUT_OF_BOUNDS_ERROR", "String identifier out of bounds MAX:" + (str.length() - 1) + ",IN:" + i);
                }
                return new StringResult(str.charAt(i) + "");
            }
            case "D": {
                List<?> list = (List<?>) args.get(0).getObject();
                NumberResult numberResult = (NumberResult) args.get(1);
                int i = numberResult.toInteger();
                if (i < 0 || i >= list.size()) {
                    return new ErrorResult("OUT_OF_BOUNDS_ERROR", "list identifier out of bounds MAX:" + (list.size() - 1) + ",IN:" + i);
                }
                return new ObjectResult(list.get(i) + "");
            }
            case "E": {
                String str = args.get(0).toString();
                ObjectResult objectResult = (ObjectResult) args.get(1);
                Number number = objectResult.toNumber(0);
                NumberResult numberResult = new NumberResult(number);
                int i = numberResult.toInteger();
                if (i < 0 || i >= str.length()) {
                    return new ErrorResult("OUT_OF_BOUNDS_ERROR", "String identifier out of bounds MAX:" + (str.length() - 1) + ",IN:" + i);
                }
                return new StringResult(str.charAt(i) + "");
            }
            case "F": {
                List<?> list = (List<?>) args.get(0).getObject();
                ObjectResult objectResult = (ObjectResult) args.get(1);
                Number number = objectResult.toNumber(0);
                NumberResult numberResult = new NumberResult(number);
                int i = numberResult.toInteger();
                if (i < 0 || i >= list.size()) {
                    return new ErrorResult("OUT_OF_BOUNDS_ERROR", "list identifier out of bounds MAX:" + (list.size() - 1) + ",IN:" + i);
                }
                return new ObjectResult(list.get(i) + "");
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("String").addArgType("String")
                        .addInfo("string index")
                        .addInfo("Get char from string by index.")
                        .setResultType("String")
        );
        argsSettings.add(
                new ArgsSetting("B")
                        .addArgType("List").addArgType("String")
                        .addInfo("list index")
                        .addInfo("Get element from list by index.")
                        .setResultType("Object")
        );
        argsSettings.add(
                new ArgsSetting("C")
                        .addArgType("String").addArgType("Number")
                        .addInfo("string index")
                        .addInfo("Get char from string by index.")
                        .setResultType("String")
        );
        argsSettings.add(
                new ArgsSetting("D")
                        .addArgType("List").addArgType("Number")
                        .addInfo("list index")
                        .addInfo("Get element from list by index.")
                        .setResultType("Object")
        );
        argsSettings.add(
                new ArgsSetting("E")
                        .addArgType("String").addArgType(".")
                        .addInfo("string index")
                        .addInfo("Get char from string by index.")
                        .setResultType("String")
        );
        argsSettings.add(
                new ArgsSetting("D")
                        .addArgType("List").addArgType(".")
                        .addInfo("list index")
                        .addInfo("Get element from list by index.")
                        .setResultType("Object")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
