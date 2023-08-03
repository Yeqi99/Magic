package cn.origincraft.magic.object;


import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;

public class SpellContextResult extends FunctionResult {
    private final SpellContext spellContext;

    public SpellContextResult(SpellContext spellContext) {
        this.spellContext = spellContext;
    }

    public SpellContext getSpellContext() {
        return spellContext;
    }
}
