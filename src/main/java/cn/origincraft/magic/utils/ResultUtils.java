package cn.origincraft.magic.utils;

import cn.origincraft.magic.function.results.ListResult;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.IntegerResult;
import dev.rgbmc.expression.results.ObjectResult;
import dev.rgbmc.expression.results.*;

import java.util.List;

public class ResultUtils {
    public static Object reduction(FunctionResult result){
        if (result instanceof IntegerResult){
            return ((IntegerResult) result).getInteger();
        }
        if (result instanceof ObjectResult){
            return ((ObjectResult) result).getObject();
        }
        if (result instanceof ListResult){
            return ((ListResult) result).getList();
        }
        if (result instanceof StringResult){
            return ((StringResult) result).getString();
        }
    }
    public static List<Object> reduction(List<FunctionResult> args){

    }
}
