package cn.origincraft.magic.function.results;

import cn.origincraft.magic.object.MagicWords;
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

    @Override
    public String toString() {
        StringBuilder result=new StringBuilder();
        for (MagicWords magicWords : getSpell().getMagicWordsList()) {
            result.append(magicWords.getOriginMagicWords()).append("\n");
        }
        return result.toString().strip();
    }
}
