package cn.origincraft.magic.function.results;

import dev.rgbmc.expression.functions.FunctionResult;

public class FloatResult extends FunctionResult {
    private final float value;

    public FloatResult(float value) {
        this.value = value;
    }

    public float getFloat() {
        return value;
    }
}
