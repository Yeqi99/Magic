package cn.origincraft.magic.function.system.variable.magic;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class SpellFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id) {
            case "A": {
                List<?> list = (List<?>) args.get(0).getObject();
                List<String> stringList = new ArrayList<>();
                for (Object o : list) {
                    stringList.add(o.toString());
                }
                Spell spell = new Spell(stringList,spellContext.getMagicManager());
                return new SpellResult(spell);
            }
            case "B": {
                String magicWords = args.get(0).toString();
                List<String> stringList = new ArrayList<>();
                stringList.add(magicWords);
                Spell spell = new Spell(stringList,spellContext.getMagicManager());
                return new SpellResult(spell);
            }
            case "C":{
                Object object = args.get(0).getObject();
                if(!(object instanceof Spell)){
                    return new ErrorResult("TYPE_ERROR","Object convert to spell failed");
                }
                return new SpellResult((Spell) object);
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings=new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("List")
                        .addInfo("stringList")
                        .addInfo("Create a spell")
                        .setResultType("Spell")
        );
        argsSettings.add(
                new ArgsSetting("B")
                        .addArgType("String")
                        .addInfo("magicWords")
                        .addInfo("Create a spell by magicWords")
                        .setResultType("Spell")
        );
        argsSettings.add(new ArgsSetting("C")
                .addArgType(".")
                .addInfo("object")
                .addInfo("Convert object to spell")
                .setResultType("Spell")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "spell";
    }
}
