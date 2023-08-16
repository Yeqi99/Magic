package cn.origincraft.magic.function.results;

import dev.rgbmc.expression.functions.FunctionResult;

public class ErrorResult extends FunctionResult {
    private final String errorId;
    private final String info;

    public ErrorResult(String errorId, String info) {
        this.errorId = errorId;
        this.info = info;
    }

    public String getErrorId() {
        return errorId;
    }

    public String getInfo() {
        return info;
    }
}
