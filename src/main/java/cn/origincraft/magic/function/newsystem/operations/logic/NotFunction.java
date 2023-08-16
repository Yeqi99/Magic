package cn.origincraft.magic.function.newsystem.operations.logic;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.BooleanResult;

import java.util.List;

public class NotFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()){
            return new BooleanResult(false);
        }
        if (args.get(0) instanceof BooleanResult value){
            return new BooleanResult(!value.getBoolean());
        }else {
            return new ErrorResult("ERROR_IN_TYPE", "Cannot convert object to boolean.");
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "not";
    }
}
