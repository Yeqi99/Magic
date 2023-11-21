package cn.origincraft.magic.function.results;

import cn.origincraft.magic.object.Spell;

public class SpellResult extends ObjectResult {


    public SpellResult(Spell value) {
        super(value);
    }

    public Spell getSpell() {
        return (Spell) getObject();
    }

    @Override
    public String getName() {
        return "Spell";
    }
    @Override
    public int len() {
        return getSpell().getMagicWordsList().size();
    }
}
