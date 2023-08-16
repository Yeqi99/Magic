package cn.origincraft.magic.function.results;

import dev.rgbmc.expression.functions.FunctionResult;
import java.util.Set;

public class SetResult extends FunctionResult {
    private final Set<Object> value;

    public SetResult(Set<Object> value) {
        this.value = value;
    }

    public Set<Object> getSet() {
        return value;
    }
}
