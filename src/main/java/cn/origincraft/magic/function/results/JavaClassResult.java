package cn.origincraft.magic.function.results;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class JavaClassResult extends ObjectResult{
    public JavaClassResult(Class<?> value){
        super(value);
    }
    public JavaClassResult(String className) throws ClassNotFoundException {
        super(Class.forName("Person"));
    }
    @Override
    public String getName() {
        return "JavaClass";
    }
}
