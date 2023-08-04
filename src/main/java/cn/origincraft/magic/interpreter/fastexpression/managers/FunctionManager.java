package cn.origincraft.magic.interpreter.fastexpression.managers;


import cn.origincraft.magic.interpreter.fastexpression.FastExpression;
import cn.origincraft.magic.interpreter.fastexpression.functions.CallableFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.interpreter.fastexpression.parameters.StringParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                throw new IllegalArgumentException("Function " + registerName + " does not exist");
            }
            return functions.get(real);
        }
    }

    public void register(FastFunction function) {
        String registerName = function.getName();
        if (functions.containsKey(registerName)) {
            throw new IllegalStateException("Function " + registerName + " already exists");
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

    public List<CallableFunction> parseExpression(String expression) {
        List<CallableFunction> callableFunctions = new ArrayList<>();
        Pattern pattern = Pattern.compile("([\\w\\p{IsHan}]+\\((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*\\))");
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {
            String functionExpression = matcher.group(0);
            Pattern functionPattern = Pattern.compile("([\\w\\p{IsHan}]+)\\((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*\\)");
            Pattern parameterPattern = Pattern.compile("[\\w\\p{IsHan}]+\\(((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*)\\)");
            Matcher functionMatcher = functionPattern.matcher(functionExpression);
            if (!functionMatcher.find()) continue;
            String functionName = functionMatcher.group(1);
            Matcher parameterMatcher = parameterPattern.matcher(functionExpression);
            if (!parameterMatcher.find()) continue;
            String parameter = parameterMatcher.group(1);
            callableFunctions.add(new CallableFunction(get(functionName), new StringParameter(parameter)));
        }
        return callableFunctions;
    }
}