package cn.origincraft.magic.function.results;

import cn.origincraft.magic.object.MagicWords;

public class MagicWordsResult extends ObjectResult {

    public MagicWordsResult(MagicWords value) {
        super(value);
    }

    public MagicWords getMagicWords() {
        return (MagicWords) getObject();
    }

    @Override
    public String getName() {
        return "MagicWords";
    }
}
