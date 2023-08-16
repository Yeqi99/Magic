package cn.origincraft.magic.function.results;

import cn.origincraft.magic.object.MagicWords;
import dev.rgbmc.expression.functions.FunctionResult;

public class MagicWordsResult extends FunctionResult {
    private final MagicWords value;

    public MagicWordsResult(MagicWords value) {
        this.value = value;
    }

    public MagicWords getMagicWords() {
        return value;
    }
}
