package cn.origincraft.magic.function.results;

import dev.rgbmc.expression.functions.FunctionResult;
import java.util.List;

public class ListResult extends FunctionResult {
    private final List<Object> value;

    public ListResult(List<Object> value) {
        this.value = value;
    }

    public List<Object> getList() {
        return value;
    }
}
