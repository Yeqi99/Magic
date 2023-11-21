package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.BooleanResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.SpellResult;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class WhileFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id) {
            case "A": {
                boolean flag = args.get(0).toBoolean(false);
                SpellContext executeContext = null;
                while (flag) {
                    for (FunctionResult functionResult : args.subList(1, args.size())) {
                        if (functionResult instanceof SpellResult) {
                            Spell spell = (Spell) functionResult.getObject();
                            executeContext = spell.execute(spellContext.getContextMap());
                            spellContext.addPrintLog(executeContext.getPrintLog());
                        }
                    }
                    if (executeContext == null) {
                        return new ErrorResult("RETURN_ERROR", "last spell need a return result");
                    }
                    FunctionResult returnResult = executeContext.getSpellReturn();
                    flag = returnResult.toBoolean(false);
                }
            }
            case "B": {
                boolean flag = args.get(0).toBoolean(false);
                if (flag) {
                    spellContext.putExecuteNext(spellContext.getExecuteIndex());
                    return new BooleanResult(true);
                } else {
                    spellContext.putExecuteIndexAllow(spellContext.getExecuteIndex(), false);
                    return new BooleanResult(false);
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
                        .addArgType("Boolean").addArgType("...")
                        .addInfo("boolean spell...")
                        .addInfo("if first arg is true then execute spells util first arg is false")
                        .setResultType("Boolean")
        );
        argsSettings.add(
                new ArgsSetting("B")
                        .addArgType("Boolean")
                        .addInfo("boolean")
                        .addInfo("if first arg is true then next still execute this line util first arg is false")
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
        return "while";
    }
}
