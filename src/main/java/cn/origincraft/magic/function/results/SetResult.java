package cn.origincraft.magic.function.results;

import dev.rgbmc.expression.functions.FunctionResult;
import java.util.Set;

public class SetResult extends FunctionResult {
    private final Set<?> value;

    public SetResult(Set<?> value) {
        this.value = value;
    }

    public Set<?> getSet() {
        return value;
    }
}
