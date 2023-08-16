package cn.origincraft.magic.function.results;

import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;

public class SpellContextResult extends FunctionResult {
    private final SpellContext value;

    public SpellContextResult(SpellContext value) {
        this.value = value;
    }

    public SpellContext getSpellContext() {
        return value;
    }
}
