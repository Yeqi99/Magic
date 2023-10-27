package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.function.OnlyStringFunction;
import cn.origincraft.magic.function.results.SpellResult;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;

import java.util.ArrayList;
import java.util.List;


public class EvalFunction extends OnlyStringFunction {

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "eval";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, String args) {
        List<String> spellWorlds=new ArrayList<>();
        spellWorlds.add(args);
        Spell spell =new Spell(spellWorlds,spellContext.getMagicManager());
        return new SpellResult(spell);
    }
}
