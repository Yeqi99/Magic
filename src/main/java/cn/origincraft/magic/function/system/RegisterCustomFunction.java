package cn.origincraft.magic.function.system;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.ArgumentsResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.NormalContext;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class RegisterCustomFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id=argsSetting.getId();
        switch (id){
            case "A":{
                Spell spell = (Spell) args.get(0).getObject();
                String name= (String) args.get(1).getObject();
                String type = (String) args.get(2).getObject();
                List<?> argsSettingList= (List<?>) args.get(3).getObject();
                List<ArgsSetting> argsSettings=new ArrayList<>();
                for (Object o : argsSettingList) {
                    argsSettings.add((ArgsSetting) o);
                }
                spellContext.getMagicManager().registerFunction(new ArgsFunction() {
                    @Override
                    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
                        NormalContext context = new NormalContext();
                        context.putVariable("args", new ArgumentsResult(args));
                        context.putVariable("argsId",new StringResult(argsSetting.getId()));
                        SpellContext temp = spell.execute(context);
                        if (!temp.hasSpellReturn()) {
                            return new NullResult();
                        }
                        return temp.getSpellReturn();
                    }

                    @Override
                    public List<ArgsSetting> getArgsSetting() {
                        return argsSettings;
                    }

                    @Override
                    public String getType() {
                        return type;
                    }

                    @Override
                    public String getName() {
                        return name;
                    }
                });
                break;
            }
            case "B":{
                Spell spell = (Spell) args.get(0).getObject();
                String name= (String) args.get(1).getObject();
                String type = (String) args.get(2).getObject();
                spellContext.getMagicManager().registerFunction(new ArgsFunction() {
                    @Override
                    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
                        NormalContext context = new NormalContext();
                        context.putVariable("args", new ArgumentsResult(args));
                        context.putVariable("argsId",new StringResult(argsSetting.getId()));
                        SpellContext temp = spell.execute(context);
                        if (!temp.hasSpellReturn()) {
                            return new NullResult();
                        }
                        return temp.getSpellReturn();
                    }

                    @Override
                    public List<ArgsSetting> getArgsSetting() {
                        List<ArgsSetting> argsSettings=new ArrayList<>();
                        argsSettings.add(
                                new ArgsSetting("Default")
                                        .addArgType("...")
                                        .addInfo("A custom method.")
                                        .addInfo("don't have argsSetting")
                                        .addInfo("normal variable: args,argsId")
                                        .setResultType("Object")
                        );
                        return argsSettings;
                    }

                    @Override
                    public String getType() {
                        return type;
                    }

                    @Override
                    public String getName() {
                        return name;
                    }
                });
                break;
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                    .addArgType("Spell").addArgType("String").addArgType("String").addArgType("List")
                        .addInfo("spell name type argsSettingList")
                        .addInfo("Register a custom method.")
                        .addInfo("spell: The spell to be registered.")
                        .addInfo("name: The name of the method.")
                        .addInfo("type: The type of the method.")
                        .addInfo("argsSettingList: use list(argsSetting(...) ...)")
                        .addInfo("normal variable: args,argsId")
                        .setResultType("Null")
        );
        argsSettings.add(
                new ArgsSetting("B")
                        .addArgType("Spell").addArgType("String").addArgType("String")
                        .addInfo("spell name type")
                        .addInfo("Register a custom method.")
                        .addInfo("spell: The spell to be registered.")
                        .addInfo("name: The name of the method.")
                        .addInfo("type: The type of the method.")
                        .addInfo("normal variable: args,argsId")
                        .setResultType("Null")

        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "registerCustom";
    }
}
