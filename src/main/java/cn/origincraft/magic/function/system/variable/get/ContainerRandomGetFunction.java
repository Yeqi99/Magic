package cn.origincraft.magic.function.system.variable.get;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ListResult;
import cn.origincraft.magic.function.results.ObjectResult;
import cn.origincraft.magic.function.results.SetResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class ContainerRandomGetFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id) {
            case "A": {
                List<?> list = (List<?>) args.get(0).getObject();
                Object result = RandomUtils.selectRandomItem(list);
                if (result==null){
                    return new ErrorResult("USAGE_ERROR","Select element failed");
                }
                return new ObjectResult(result);
            }
            case "B": {
                List<?> list = (List<?>) args.get(0).getObject();
                List<?> weights = (List<?>) args.get(1).getObject();
                Object result = RandomUtils.selectRandomItem(list,weights);
                if (result==null){
                    return new ErrorResult("USAGE_ERROR","Select element failed");
                }
                return new ObjectResult(result);
            }
        }
        return null;
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("List")
                        .addInfo("list")
                        .addInfo("Get object from list")
                        .setResultType("Object")
        );
        argsSettings.add(
                new ArgsSetting("B")
                        .addArgType("List").addArgType("List")
                        .addInfo("list list")
                        .addInfo("Get object from value list and weight list")
                        .addInfo("weight list must be double list")
                        .setResultType("Object")
        );
        return argsSettings;
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
