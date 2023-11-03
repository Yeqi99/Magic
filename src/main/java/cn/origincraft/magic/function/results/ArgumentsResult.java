package cn.origincraft.magic.function.results;


import cn.origincraft.magic.expression.functions.FunctionResult;

import java.util.List;

public class ArgumentsResult extends ObjectResult {

    public ArgumentsResult(List<FunctionResult> args) {
        super(args);
    }

    public List<FunctionResult> getArgs() {
        return (List<FunctionResult>) getObject();
    }

    @Override
    public String getName() {
        return "Arguments";
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (FunctionResult arg : getArgs()) {
            s.append(arg.toString()).append(",");
        }
        return s.toString();
    }
}
