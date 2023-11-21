package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.SpellResult;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class IfFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id =argsSetting.getId();
        switch (id){
            case "A":{
                boolean flag= args.get(0).toBoolean(false);
                if (!flag){
                    return new NullResult();
                }
                SpellContext executeContext=null;
                for (FunctionResult functionResult : args.subList(1, args.size())) {
                    if (functionResult instanceof SpellResult) {
                        Spell spell = (Spell) functionResult.getObject();
                        executeContext = spell.execute(spellContext.getContextMap());
                        spellContext.addPrintLog(executeContext.getPrintLog());
                    }
                }
                if (executeContext==null){
                    return new ErrorResult("TYPE_ERROR","If requires at least one spell parameter");
                }
                return executeContext.getSpellReturn();
            }
            case "B":{
                boolean flag=args.get(0).toBoolean(false);
                if (!flag){
                    spellContext.putExecuteIndexAllow(spellContext.getExecuteIndex(), false);
                }
                return new NullResult();
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
                        .addInfo("if first arg is true then execute spells")
                        .setResultType("Object")
        );
        argsSettings.add(
                new ArgsSetting("B")
                        .addArgType("Boolean")
                        .addInfo("boolean")
                        .addInfo("if first arg is false then skip line and execute the next line")
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
        return "if";
    }
}
