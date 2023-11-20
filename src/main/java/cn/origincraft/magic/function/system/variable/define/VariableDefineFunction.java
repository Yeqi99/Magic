package cn.origincraft.magic.function.system.variable.define;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.ParseType;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class VariableDefineFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id) {
            case "A": {
                String varName = args.get(0).toString();
                spellContext.getContextMap().putVariable(varName, args.get(1));
                return args.get(1);
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
                        .addInfo("Define a variable to variableMap.")
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
        return "variableDefine";
    }
}
