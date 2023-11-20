package cn.origincraft.magic.function.system.control.execute;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.ArgumentsResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.NormalContext;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class ArgsSpellExecuteFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id =argsSetting.getId();
        switch (id){
            case "A":{
                Spell spell= (Spell) args.get(0).getObject();
                List<FunctionResult> arguments= (List<FunctionResult>) args.get(1).getObject();
                NormalContext context = new NormalContext();
                context.putVariable("args", new ArgumentsResult(arguments));
                SpellContext executeContext = spell.execute(context);
                if (!executeContext.hasSpellReturn()) {
                    return new NullResult();
                } else {
                    return executeContext.getSpellReturn();
                }
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("Spell").addArgType("Arguments")
                        .addInfo("spell args")
                        .addInfo("execute spell with args")
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
        return "argsSpellExecute";
    }
}
