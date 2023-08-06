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
    public List<String> getFunctionRealNames(){
        return new ArrayList<>(functions.keySet());
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
        // 正则表达式匹配函数名和括号内的参数
        Pattern pattern = Pattern.compile("([\\w\\p{IsHan}]+)\\(((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*)\\)");
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {
            String functionName = matcher.group(1); // 函数名
            String parameter = matcher.group(2); // 参数
            callableFunctions.add(new CallableFunction(get(functionName), new StringParameter(parameter)));
        }
        return callableFunctions;
    }

    public List<Object> parseParaExpression(String expression) {
        List<Object> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("([\\w\\p{IsHan}\\.]+(?:\\((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*\\))?)|(==|!=|>=|<=|>|<)");
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()) {
            String token = matcher.group(1);
            if (token == null) {
                // 如果 group(1) 是 null，那么运算符就应在 group(2) 中
                token = matcher.group(2);
            }
            if (token.endsWith(")")) {
                // 这是一个函数调用
                Pattern functionPattern = Pattern.compile("([\\w\\p{IsHan}]+)\\(((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*)\\)");
                Matcher functionMatcher = functionPattern.matcher(token);
                if (functionMatcher.find()) {
                    String functionName = functionMatcher.group(1);
                    String parameter = functionMatcher.group(2); // 使用相同的匹配器获取参数
                    result.add(new CallableFunction(get(functionName), new StringParameter(parameter)));
                }
            } else {
                // 这是一个字符串或者是一个比较运算符
                result.add(token);
            }
        }
        return result;
    }

//    public List<Object> parseParaExpression(String expression) {
//        List<Object> result = new ArrayList<>();
//        Pattern pattern = Pattern.compile("([\\w\\p{IsHan}\\.]+(?:\\((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*\\))?)");
//        Matcher matcher = pattern.matcher(expression);
//
//        while (matcher.find()) {
//            String token = matcher.group(1);
//            if (token.endsWith(")")) {
//                // 这是一个函数调用
//                Pattern functionPattern = Pattern.compile("([\\w\\p{IsHan}]+)\\(((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*)\\)");
//                Matcher functionMatcher = functionPattern.matcher(token);
//                if (functionMatcher.find()) {
//                    String functionName = functionMatcher.group(1);
//                    String parameter = functionMatcher.group(2); // 使用相同的匹配器获取参数
//                    result.add(new CallableFunction(get(functionName), new StringParameter(parameter)));
//                }
//            } else {
//                // 这是一个字符串
//                result.add(token);
//            }
//        }
//        return result;
//    }
}