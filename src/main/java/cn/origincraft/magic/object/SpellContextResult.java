package cn.origincraft.magic.object;


import cn.origincraft.magic.function.results.ObjectResult;

public class SpellContextResult extends ObjectResult {

    public SpellContextResult(SpellContext spellContext) {
        super(spellContext);
    }

    public SpellContext getSpellContext() {
        return (SpellContext) getObject();
    }

    @Override
    public String getName() {
        return "SpellContext";
    }
}
