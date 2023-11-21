package cn.origincraft.magic.function.system.info;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class TypeFunction extends ArgsFunction {
    @Override
    public String getName() {
        return "type";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id){
            case "A":{
                return new StringResult(args.get(0).getName());
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
                        .addInfo("return is this object type")
                        .setResultType("String")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
