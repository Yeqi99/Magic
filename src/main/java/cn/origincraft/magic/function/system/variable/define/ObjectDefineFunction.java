package cn.origincraft.magic.function.system.variable.define;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.ParseType;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.ObjectResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class ObjectDefineFunction extends ArgsFunction {

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id) {
            case "A": {
                String varName = args.get(0).toString();
                Object object = args.get(1).getObject();
                spellContext.getContextMap().putObject(varName, object);
                return new ObjectResult(object);
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        setParseType(0, ParseType.ORIGIN);
        setParseType(1, ParseType.ALL);
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("String").addArgType(".")
                        .addInfo("varName value")
                        .addInfo("Define a variable to objectMap.")
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
        return "objectDefine";
    }
}
