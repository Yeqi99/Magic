package cn.origincraft.magic.function.system.variable.container;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetFunction extends ArgsFunction {

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id) {
            case "A":{
                Object object=args.get(0).getObject();
                if(!(object instanceof Set<?>)){
                    return new ErrorResult("TYPE_ERROR","Object convert to set failed");
                }
                return new SetResult((Set<?>) object);
            }
            case "B": {
                Set<Object> resultSet = new HashSet<>();
                for (FunctionResult arg : args) {
                    resultSet.add(arg.getObject());
                }
                return new SetResult(resultSet);
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType(".")
                        .addInfo("object")
                        .addInfo("Convert object to list")
                        .setResultType("Set")
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
        return "set";
    }
}
