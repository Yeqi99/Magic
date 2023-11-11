package cn.origincraft.magic.function;

import cn.origincraft.magic.expression.functions.FastFunction;

import java.util.List;

public abstract class FormatFunction implements FastFunction {
    public abstract List<String> getUsage();
}
