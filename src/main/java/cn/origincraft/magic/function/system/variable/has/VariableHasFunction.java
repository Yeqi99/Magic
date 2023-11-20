package cn.origincraft.magic.function.system.variable.has;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.ParseType;
import cn.origincraft.magic.function.results.BooleanResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class VariableHasFunction extends ArgsFunction {


    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id) {
            case "A": {
                String varName = args.get(0).toString();
                return new BooleanResult(spellContext.getContextMap().hasVariable(varName));
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
                        .addArgType("String")
                        .addInfo("varName")
                        .addInfo("Check if the variable exists in variableMap.")
                        .setResultType("Boolean")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "variableHas";
    }
}
