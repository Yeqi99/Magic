package cn.origincraft.magic.function.system.variable.meta;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.FloatResult;
import cn.origincraft.magic.function.results.ListResult;
import cn.origincraft.magic.function.results.LongResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtil;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.*;

import java.util.List;

public class StringFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("STRING_FUNCTION_ARGS_ERROR", "String don't have enough args.");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (FunctionResult functionResult : args) {
            if (functionResult instanceof StringResult v) {
                stringBuilder.append(v.getString());
            }else if (functionResult instanceof IntegerResult v) {
                stringBuilder.append(v.getInteger());
            }else if (functionResult instanceof DoubleResult v) {
                stringBuilder.append(v.getDouble());
            }else if (functionResult instanceof BooleanResult v) {
                stringBuilder.append(v.getBoolean());
            }else if(functionResult instanceof LongResult){
                stringBuilder.append(((LongResult) functionResult).getLong());
            }else if (functionResult instanceof FloatResult v) {
                stringBuilder.append(v.getFloat());
            }else if(functionResult instanceof ObjectResult v){
                stringBuilder.append(v.getObject().toString());
            }else if(functionResult instanceof ListResult){
                List<Object> list=((ListResult) functionResult).getList();
                for (Object o : list) {
                    if (o instanceof String){
                        stringBuilder.append(o).append("\n");
                    }
                }
            } else {
                return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
            }
        }
        return new StringResult(stringBuilder.toString());
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "string";
    }
}
