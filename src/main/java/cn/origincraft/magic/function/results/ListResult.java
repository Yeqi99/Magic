package cn.origincraft.magic.function.results;

import dev.rgbmc.expression.functions.FunctionResult;
import java.util.List;

public class ListResult extends FunctionResult {
    private final List<?> value;

    public ListResult(List<?> value) {
        this.value = value;
    }

    public List<?> getList() {
        return value;
    }
}
