package cn.origincraft.magic.utils;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.MagicWords;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public static FunctionResult reduction(Object o){
        if (o instanceof String){
            return new StringResult((String) o);
        }
        if (o instanceof Integer){
            return new IntegerResult((Integer) o);
        }
        if (o instanceof Long){
            return new LongResult((Long) o);
        }
        if (o instanceof Float){
            return new FloatResult((Float) o);
        }
        if (o instanceof Double){
            return new DoubleResult((Double) o);
        }
        if (o instanceof Boolean){
            return new BooleanResult((Boolean) o);
        }
        if (o instanceof List){
            return new ListResult((List<Object>) o);
        }
        if (o instanceof Map){
            return new MapResult((Map<Object,Object>) o);
        }
        if (o instanceof Set){
            return new SetResult((Set<Object>) o);
        }
        if (o instanceof SpellContext){
            return new SpellContextResult((SpellContext) o);
        }
        if (o instanceof Spell){
            return new SpellResult((Spell) o);
        }
        if (o instanceof MagicWords){
            return new MagicWordsResult((MagicWords) o);
        }
        if (o instanceof FunctionResult){
            return (FunctionResult) o;
        }
        return new ObjectResult(o);
    }
}
