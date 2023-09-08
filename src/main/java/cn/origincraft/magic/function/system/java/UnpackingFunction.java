package cn.origincraft.magic.function.system.java;

import cn.origincraft.magic.function.FunctionRegister;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.ObjectResult;
import dev.rgbmc.expression.results.StringResult;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class UnpackingFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size()<2) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "Unpacking function requires at least two arguments.");
        }
        FunctionResult objResult=args.get(0);
        FunctionResult nameResult=args.get(1);
        if (!(objResult instanceof ObjectResult)){
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
        if (!(nameResult instanceof StringResult)){
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
        Object object=((ObjectResult) objResult).getObject();
        String name=((StringResult) nameResult).getString();
        Class<?> clazz=object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();
        for (Field field : fields) {
            try {
                spellContext.getContextMap().putVariable(name+"."+field.getName(), new ObjectResult(field.get(object)));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        for (Method method : methods) {
            spellContext.getContextMap().putVariable(name+".m."+method.getName(), new ObjectResult(method));
        }
        spellContext.getContextMap().putVariable(name+".o", new ObjectResult(object));
        return new NullResult();
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "unpacking";
    }
}
