package cn.origincraft.magic.function.system.operations.arithmetic;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtil;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.*;

import java.util.*;

public class AdditionFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        double result=0;
        for (FunctionResult arg : args) {
            if (arg instanceof IntegerResult value){
                result+=value.getInteger();
            }else if (arg instanceof DoubleResult value){
                result+=value.getDouble();
            }else if (arg instanceof BooleanResult value){
                result+=value.getBoolean()?1:0;
            }else if (arg instanceof StringResult value){
                if (VariableUtil.tryDouble(value.getString())){
                    result+=Double.parseDouble(value.getString());
                }else {
                    return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
                }
            }else if (arg instanceof ObjectResult value){
                if (value.getObject() instanceof Integer){
                    result+=(Integer) value.getObject();
                }else if (value.getObject() instanceof Double){
                    result+=(Double) value.getObject();
                }else if (value.getObject() instanceof Boolean){
                    result+=(Boolean) value.getObject()?1:0;
                }else if (value.getObject() instanceof String){
                    if (((String) value.getObject()).matches("-?\\d+(\\.\\d+)?")){
                        result+=Double.parseDouble((String) value.getObject());
                    }else {
                        return new ErrorResult("ERROR_IN_TYPE", "Cannot convert string to number.");
                    }
                }else {
                    return new ErrorResult("ERROR_IN_TYPE", "Cannot convert object to number.");
                }
            }
        }
        if (VariableUtil.hasFractionalPart(result)) {
            return new DoubleResult(result);
        } else {
            return new IntegerResult((int) result);
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "addition";
    }
}
