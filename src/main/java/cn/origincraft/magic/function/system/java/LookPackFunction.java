package cn.origincraft.magic.function.system.java;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ListResult;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.StringResult;

import java.util.ArrayList;
import java.util.List;

public class LookPackFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()){
            return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments don't have enough args.");
        }
        FunctionResult packNameResult=args.get(0);
        if (!(packNameResult instanceof StringResult)){
            return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments type error.");
        }
        String packName=((StringResult) packNameResult).getString();
        if (!spellContext.getContextMap().hasVariable(packName+".o")){
            return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments type error.");
        }
        List<Object> result=new ArrayList<>();
        for (String variableName : spellContext.getContextMap().getVariableNames()) {
            if (variableName.startsWith(packName+".")){
                result.add(variableName);
            }
        }
        return new ListResult(result);
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "lookpack";
    }
}
