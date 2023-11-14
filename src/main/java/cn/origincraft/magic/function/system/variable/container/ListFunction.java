package cn.origincraft.magic.function.system.variable.container;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;
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
