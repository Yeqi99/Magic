package cn.origincraft.magic.function.system.java;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ObjectResult;
import cn.origincraft.magic.object.SpellContext;

import java.lang.reflect.Field;
import java.util.List;

public class PackagingFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size()<2){
            return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments don't have enough args.");
        }
        FunctionResult nameResult=args.get(0);
        if (!(nameResult instanceof ObjectResult)){
            return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments type error.");
        }
        String name=((ObjectResult) nameResult).getObject().toString();
        if (!spellContext.getContextMap().hasVariable(name+".o")){
            return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments type error.");
        }
        Object obj=spellContext.getContextMap().getVariable(name+".o");
        Class<?> clazz=obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName=field.getName();
            String varName=name+"."+fieldName;
            if (spellContext.getContextMap().hasVariable(varName)){
                Object value=spellContext.getContextMap().getVariable(varName);
                spellContext.getContextMap().removeVariable(varName);
                try {
                    field.set(obj,value);
                } catch (IllegalAccessException e) {
                    return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments type error.");
                }
            }
        }
        spellContext.getContextMap().removeVariable(name+".o");
        return new ObjectResult(obj);
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "packaging";
    }
}
