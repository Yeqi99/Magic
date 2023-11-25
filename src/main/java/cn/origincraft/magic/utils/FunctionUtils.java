package cn.origincraft.magic.utils;

import cn.origincraft.magic.expression.functions.CallableFunction;
import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.expression.managers.FunctionManager;
import cn.origincraft.magic.expression.parameters.StringParameter;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.*;

import java.util.*;

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
//    public static List<Object> parseParaExpression(String expression, FunctionManager manager) {
//        List<Object> result = new ArrayList<>();
//        Pattern pattern = Pattern.compile("([\\w\\p{L}]+(?:\\((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*\\))?)|(==|!=|>=|<=|>|<|%|&|\\+|-|\\*|/|!|\\^|\\$|#|@|~|\\?|,|\\[|]|:|;|'|\\\\|\\|)");
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
//                result.add(token);
//            }
//        }
//        return result;
//    }
    public static List<Object> parseParaExpression(String expression, FunctionManager manager) {
        List<Object> result = new ArrayList<>();
        List<String> contexts=parseCode(expression);
        for (String context : contexts) {
            if (context.endsWith(")")){
                String functionName=extractMethodName(context);
                String para= extractContent(context);
                if(manager.get(functionName) instanceof ArgsFunction){
                    assert para != null;
                    para=para.strip();
                }
                result.add(new CallableFunction(manager.get(functionName), new StringParameter(para)));
            }else {
                result.add(context);
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
    public static List<String> parseCode(String code) {
        List<String> outData = new ArrayList<>();
        if (code.isEmpty()){
            return new ArrayList<>();
        }
        while (code.charAt(0) == ' ') {
            if (code.length()<2){
                return outData;
            }
            code = code.substring(1);
        }

        boolean putBegin = false;
        int begin = 0;
        int length = 0;
        int bracketNumber = 0;

        for (int end = 0; end < code.length(); end++) {
            char currentChar = code.charAt(end);
            if (currentChar == '(') {
                bracketNumber++;
            } else if (currentChar == ')') {
                bracketNumber--;
            } else if (currentChar == ' ' && bracketNumber == 0) {
                if (!putBegin || (length != 0 && length != 1)) {
                    putBegin = true;
                    outData.add(code.substring(begin, begin + length));
                }
                begin += length;
                length = 0;
            }
            length++;
        }

        if (begin != code.length()) {
            outData.add(code.substring(begin));
        }

        for (int index = 0; index < outData.size(); index++) {
            String item = outData.get(index);
            if (item.charAt(0) != ' ') {
                continue;
            }
            outData.set(index, item.substring(1));
        }

        return outData;
    }
    public static String extractContent(String input) {
        int startIndex = input.indexOf('(');
        int endIndex = input.lastIndexOf(')');

        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return input.substring(startIndex + 1, endIndex);
        } else {
            return null;
        }
    }
    public static String extractMethodName(String input) {
        int startIndex = input.indexOf('(');

        if (startIndex != -1) {
            return input.substring(0, startIndex);
        } else {
            return null;
        }
    }
}
