package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.BooleanResult;

import java.util.List;

public class WhileFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()){
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "While function requires at least one argument.");
        }
        if (args.get(0) instanceof BooleanResult v){
            if (v.getBoolean()){
                spellContext.putExecuteNext(spellContext.getExecuteIndex());
                return new BooleanResult(true);
            }else {
                spellContext.putExecuteIndexAllow(spellContext.getExecuteIndex(),false);
                return new BooleanResult(false);
            }
        }else {
            return new ErrorResult("INVALID_ARGUMENT", "While function requires a boolean argument.");
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "while";
    }
}
