package cn.origincraft.magic.expression.managers;


import cn.origincraft.magic.expression.FastExpression;
import cn.origincraft.magic.expression.functions.CallableFunction;
import cn.origincraft.magic.expression.functions.FastFunction;
import cn.origincraft.magic.expression.functions.FunctionParameter;
import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.expression.parameters.StringParameter;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.utils.FunctionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionManager {
    private final Map<String, FastFunction> functions;
    private final FastExpression instance;

    public FunctionManager(FastExpression instance) {
        functions = new HashMap<>();
        this.instance = instance;
    }

    public FunctionResult call(String registerName, FunctionParameter parameter) {
        return get(registerName).call(parameter);
    }

    public FastFunction get(String registerName) {
        if (functions.containsKey(registerName)) {
            return functions.get(registerName);
        } else {
            String real = instance.getAliasesManager().getRealName(registerName);
            if (real.equals(registerName)) {
                System.out.println("Function " + registerName + " does not exist");
                return functions.get("null");
            }
            return functions.get(real);
        }
    }

    public void register(FastFunction function) {
        String registerName = function.getName();
        if (functions.containsKey(registerName)) {
            System.out.println("Function " + registerName + " already exists");
        } else {
            functions.put(registerName, function);
        }
    }

    public void register(FastFunction function, String... aliases) {
        register(function);
        instance.getAliasesManager().registerAliases(function.getName(), aliases);
    }

    private boolean exists(String registerName) {
        return functions.containsKey(registerName);
    }

//    public List<CallableFunction> parseExpression(String expression) {
//        List<CallableFunction> callableFunctions = new ArrayList<>();
//        Pattern pattern = Pattern.compile("([\\w\\p{L}]+)\\(((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*)\\)");
//        Matcher matcher = pattern.matcher(expression);
//        while (matcher.find()) {
//            String functionName = matcher.group(1);
//            String parameter = matcher.group(2);
//            callableFunctions.add(new CallableFunction(get(functionName), new StringParameter(parameter)));
//        }
//        return callableFunctions;
//    }
    public List<CallableFunction> parseExpression(String expression) {
        List<CallableFunction> callableFunctions = new ArrayList<>();
        List<String> contexts= FunctionUtils.parseCode(expression);
        for (String context : contexts) {
            if (context.endsWith(")")){
                String functionName=FunctionUtils.extractMethodName(context);
                String para= FunctionUtils.extractContent(context);
                if(get(functionName) instanceof ArgsFunction){
                    assert para != null;
                    para=para.strip();
                }
                callableFunctions.add(new CallableFunction(get(functionName), new StringParameter(para)));
            }
        }
        return callableFunctions;
    }

    public Map<String, FastFunction> getRegistry() {
        return functions;
    }
}
