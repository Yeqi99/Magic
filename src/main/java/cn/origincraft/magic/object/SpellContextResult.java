package cn.origincraft.magic.object;


import dev.rgbmc.expression.functions.FunctionResult;

public class SpellContextResult extends FunctionResult {
    private final SpellContext spellContext;

    public SpellContextResult(SpellContext spellContext) {
        this.spellContext = spellContext;
    }

    public SpellContext getSpellContext() {
        return spellContext;
    }
}
