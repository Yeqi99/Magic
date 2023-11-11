package cn.origincraft.magic.utils;

import cn.origincraft.magic.expression.functions.CallableFunction;
import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.expression.managers.FunctionManager;
import cn.origincraft.magic.expression.parameters.StringParameter;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionUtils {
//    public static List<Object> parseParaExpression(String expression, FunctionManager manager) {
//        List<Object> result = new ArrayList<>();
//        Pattern pattern = Pattern.compile("([\\w\\p{L}.]+(?:\\((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*\\))?)|(==|!=|>=|<=|>|<|%|&|\\+|-|\\*|/|!|\\^|\\$|#|@|~|\\?|,|\\[|]|:|;|'|\\\\|\\|)");
//        Matcher matcher = pattern.matcher(expression);
//
//        while (matcher.find()) {
//            String token = matcher.group(1);
//            if (token == null) {
//                token = matcher.group(2);
//            }
//            if (token.endsWith(")")) {
//                // 这是一个函数调用
//                Pattern functionPattern = Pattern.compile("([\\w\\p{L}]+)\\(((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*)\\)");
//                Matcher functionMatcher = functionPattern.matcher(token);
//                if (functionMatcher.find()) {
//                    String functionName = functionMatcher.group(1);
//                    String parameter = functionMatcher.group(2); // 使用相同的匹配器获取参数
//                    result.add(new CallableFunction(manager.get(functionName), new StringParameter(parameter)));
//                }
//            } else {
//                // 这是一个字符串或者是一个比较运算符
//                result.add(token);
//            }
//        }
//        return result;
//    }
    public static List<Object> parseParaExpression(String expression, FunctionManager manager) {
        List<Object> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("([\\w\\p{L}]+(?:\\((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*\\))?)|(==|!=|>=|<=|>|<|%|&|\\+|-|\\*|/|!|\\^|\\$|#|@|~|\\?|,|\\[|]|:|;|'|\\\\|\\|\\.)");
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()) {
            String token = matcher.group(1);
            if (token == null) {
                token = matcher.group(2);
            }
            if (token.endsWith(")")) {
                // 这是一个函数调用
                Pattern functionPattern = Pattern.compile("([\\w\\p{L}]+)\\(((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*)\\)");
                Matcher functionMatcher = functionPattern.matcher(token);
                if (functionMatcher.find()) {
                    String functionName = functionMatcher.group(1);
                    String parameter = functionMatcher.group(2); // 使用相同的匹配器获取参数
                    result.add(new CallableFunction(manager.get(functionName), new StringParameter(parameter)));
                }
            } else {
                // 这是一个字符串或者是一个比较运算符
                result.add(token);
            }
        }
        return result;
    }
    public static ArgsSetting createArgsSetting(String type, String info, String resultType) {
        String[] types = type.split(" ");
        List<String> typeList = new ArrayList<>(Arrays.asList(types));
        String[] infos = info.split("\n");
        List<String> infoList = new ArrayList<>(Arrays.asList(infos));
        return new ArgsSetting(typeList, infoList, resultType);
    }

    public static ArgsSetting createArgsSetting(String id, String type, String info, String resultType) {
        String[] types = type.split(" ");
        List<String> typeList = new ArrayList<>(Arrays.asList(types));
        String[] infos = info.split("\n");
        List<String> infoList = new ArrayList<>(Arrays.asList(infos));
        return new ArgsSetting(id, typeList, infoList, resultType);
    }
    public static FunctionResult parseFunction(SpellContext spellContext,Object o){
        CallableFunction function = (CallableFunction) o;
        StringParameter stringParameter =
                (StringParameter) function.getParameter();
        spellContext.putExecuteParameter(stringParameter.getString());
        return function.getFunction().call(
                new SpellContextParameter(spellContext)
        );
    }
    public static FunctionResult parseVariable(SpellContext spellContext,Object o){
        String value = (String) o;
        if (spellContext
                .getContextMap()
                .hasVariable(value)) {
            Object v = spellContext.getContextMap().getVariable(value);

            if (v instanceof ErrorResult) {
                return (ErrorResult) v;
            }
            if (v instanceof Integer) {
                return new IntegerResult((Integer) v);
            } else if (v instanceof Double) {
                return new DoubleResult((Double) v);
            } else if (v instanceof String) {
                return new StringResult((String) v);
            } else if (v instanceof List) {
                return new ListResult((List<?>) v);
            } else if (v instanceof Map) {
                return new MapResult((Map<?, ?>) v);
            } else if (v instanceof Set) {
                return new SetResult((Set<?>) v);
            } else if (v instanceof SpellContext) {
                return new SpellContextResult((SpellContext) v);
            } else if (v instanceof Spell) {
                return new SpellResult((Spell) v);
            } else if (v instanceof MagicWords) {
                return new MagicWordsResult((MagicWords) v);
            } else if (v instanceof Boolean) {
                return new BooleanResult((Boolean) v);
            } else if (v instanceof Long) {
                return new LongResult((Long) v);
            } else if (v instanceof Float) {
                return new FloatResult((Float) v);
            } else if (v instanceof FunctionResult) {
                return (FunctionResult) v;
            } else {
                return new ObjectResult(v);
            }
        } else {
            return new StringResult(value);
        }
    }
}
