package cn.origincraft.magic.function.system.reflect;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.JavaClassResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.ObjectResult;
import cn.origincraft.magic.object.SpellContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class JavaClassFunction extends ArgsFunction {
    @Override
    public String getName() {
        return "javaClass";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id =argsSetting.getId();
        try {
            switch (id){
                case "A":{
                    String className= args.get(0).toString();
                    return new JavaClassResult(className);
                }
                case "B":{
                    Class<?> clazz = (Class<?>) args.get(0).getObject();

                    Object instance;

                    if (args.size() == 1) {
                        // 无参数构造函数
                        instance = clazz.getDeclaredConstructor().newInstance();
                    } else {
                        // 带参数的构造函数
                        Object[] constructorArgs = new Object[args.size() - 1];
                        Class<?>[] constructorArgTypes = new Class<?>[args.size() - 1];

                        for (int i = 1; i < args.size(); i++) {
                            constructorArgs[i - 1] = args.get(i).getObject();
                            constructorArgTypes[i - 1] = constructorArgs[i - 1].getClass();
                        }

                        Constructor<?> constructor = clazz.getConstructor(constructorArgTypes);
                        instance = constructor.newInstance(constructorArgs);
                    }
                    return new ObjectResult(instance);
                }
                case "C": {
                    Object obj = args.get(0).getObject();
                    Class<?> clazz = obj.getClass();
                    return new JavaClassResult(clazz);
                }
                case "D": {
                    Object obj = args.get(0).getObject();
                    String methodName = args.get(1).toString();

                    Object[] methodArgs = new Object[args.size() - 2];
                    Class<?>[] methodArgTypes = new Class<?>[args.size() - 2];

                    for (int i = 2; i < args.size(); i++) {
                        methodArgs[i - 2] = args.get(i).getObject();
                        methodArgTypes[i - 2] = methodArgs[i - 2].getClass();
                    }

                    Method method = obj.getClass().getMethod(methodName, methodArgTypes);
                    Object returnValue = method.invoke(obj, methodArgs);

                    if (returnValue == null) {
                        return new NullResult();
                    } else {
                        return new ObjectResult(returnValue);
                    }
                }
                case "E": {
                    String urlString = args.get(0).toString();
                    String className = args.get(1).toString();
                    URL url = new URL(urlString);

                    URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url}, this.getClass().getClassLoader());
                    Class<?> clazz = urlClassLoader.loadClass(className);
                    return new JavaClassResult(clazz);
                }
            }
            return new NullResult();
        }catch (Exception e){
            return new ErrorResult("JAVA_ERROR",e.getMessage());
        }
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings=new ArrayList<>();
        argsSettings.add(new ArgsSetting("A")
                .addArgType("String")
                .addInfo("className")
                .addInfo("get java class from name")
                .setResultType("JavaClass")
        );
        argsSettings.add(new ArgsSetting("B")
                .addArgType("JavaClass").addArgType("...")
                .addInfo("javaClass args...")
                .addInfo("get object from javaClass and args")
                .setResultType("Object")
        );
        argsSettings.add(new ArgsSetting("C")
                .addArgType(".")
                .addInfo("Object")
                .addInfo("gat java class from object")
                .setResultType("JavaClass")
        );
        argsSettings.add(new ArgsSetting("D")
                .addArgType(".").addArgType("String").addArgType("...")
                .addInfo("object methodName methodArgs...")
                .addInfo("execute object's method")
                .setResultType("Object")
        );
        argsSettings.add(new ArgsSetting("E")
                .addArgType("String").addArgType("String")
                .addInfo("url className ")
                .addInfo("get java class from url and name")
                .setResultType("JavaClass")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
