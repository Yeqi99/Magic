package cn.origincraft.magic.function.results;

import cn.origincraft.magic.object.Spell;
import dev.rgbmc.expression.functions.FunctionResult;

public class SpellResult extends FunctionResult {
    private final Spell value;

    public SpellResult(Spell value) {
        this.value = value;
    }

    public Spell getSpell() {
        return value;
    }
}
