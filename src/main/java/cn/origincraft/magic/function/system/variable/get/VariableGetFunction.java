package cn.origincraft.magic.function.system.variable.get;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.ContextMap;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VariableGetFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id =argsSetting.getId();
        switch (id){
            case "A":{
                String varName = args.get(0).toString();
                return (FunctionResult) spellContext.getContextMap().getVariable(varName);
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("String")
                        .addInfo("varName")
                        .addInfo("Get variableMap variable.")
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
        return "getVariable";
    }
}
