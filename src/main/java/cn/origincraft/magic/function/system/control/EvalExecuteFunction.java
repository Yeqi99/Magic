package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.OnlyStringFunction;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class EvalExecuteFunction extends OnlyStringFunction {
    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "evalExecute";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, String args) {
        List<String> spellWorlds = new ArrayList<>();
        spellWorlds.add(args);
        Spell spell = new Spell(spellWorlds, spellContext.getMagicManager());
        SpellContext evalSpellContext = spell.execute(spellContext.getContextMap());
        spellContext.addPrintLog(evalSpellContext.getPrintLog());
        return evalSpellContext.getSpellReturn();
    }

    @Override
    public List<String> getUsage() {
        List<String> usage = new ArrayList<>();
        usage.add("evalExecute <code>");
        return usage;
    }
}
