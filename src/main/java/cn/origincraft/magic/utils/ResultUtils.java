package cn.origincraft.magic.utils;

import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.MagicWords;
import cn.origincraft.magic.object.SpellContextResult;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.*;

import java.util.List;

public class ResultUtils {
    public static Object reduction(FunctionResult result){
        if (result instanceof StringResult){
            return ((StringResult) result).getString();
        }
        if (result instanceof IntegerResult){
            return ((IntegerResult) result).getInteger();
        }
        if (result instanceof LongResult){
            return ((LongResult) result).getLong();
        }
        if (result instanceof FloatResult){
            return ((FloatResult) result).getFloat();
        }
        if (result instanceof DoubleResult){
            return ((DoubleResult) result).getDouble();
        }
        if (result instanceof ObjectResult){
            return ((ObjectResult) result).getObject();
        }
        if (result instanceof ListResult){
            return ((ListResult) result).getList();
        }
        if (result instanceof MapResult){
            return ((MapResult) result).getMap();
        }
        if (result instanceof BooleanResult){
            return ((BooleanResult) result).getBoolean();
        }
        if (result instanceof NullResult){
            return null;
        }
        if (result instanceof SpellResult){
            return ((SpellResult) result).getSpell();
        }
        if (result instanceof SetResult){
            return ((SetResult) result).getSet();
        }
        if (result instanceof SpellContextResult){
            return ((SpellContextResult) result).getSpellContext();
        }
        if (result instanceof ArgumentsResult){
            return ((ArgumentsResult) result).getArgs();
        }
        if (result instanceof MagicWordsResult){
            return ((MagicWordsResult) result).getMagicWords();
        }
        return null;
    }
    public static List<Object> reduction(List<FunctionResult> args){
        List<Object> resultList=new java.util.ArrayList<>();
        for (FunctionResult arg : args) {
            resultList.add(reduction(arg));
        }
        return resultList;
    }
    public static Object[] reductionToArray(List<FunctionResult> args){
        Object[] resultArray=new Object[args.size()];
        for (int i = 0; i < args.size(); i++) {
            resultArray[i]=reduction(args.get(i));
        }
        return resultArray;
    }
}
