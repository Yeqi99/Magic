package cn.origincraft.magic.function.results;


import dev.rgbmc.expression.functions.FunctionResult;

public class LongResult extends FunctionResult {
    private final long value;

    public LongResult(long value) {
        this.value = value;
    }


    public long getLong() {
        return value;
    }
}
