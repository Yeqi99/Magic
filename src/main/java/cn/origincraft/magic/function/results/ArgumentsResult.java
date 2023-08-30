package cn.origincraft.magic.function.results;

import dev.rgbmc.expression.functions.FunctionResult;

import java.util.List;

public class ArgumentsResult extends FunctionResult{
    private final List<FunctionResult> args;

    public ArgumentsResult(List<FunctionResult> args) {
        this.args = args;
    }

    public List<FunctionResult> getArgs() {
        return args;
    }
}
