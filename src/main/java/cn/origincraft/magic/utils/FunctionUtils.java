package cn.origincraft.magic.utils;

import dev.rgbmc.expression.functions.CallableFunction;
import dev.rgbmc.expression.managers.FunctionManager;
import dev.rgbmc.expression.parameters.StringParameter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionUtils {
    public static List<Object> parseParaExpression(String expression, FunctionManager manager) {
        List<Object> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("([\\w\\p{L}.]+(?:\\((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*\\))?)|(==|!=|>=|<=|>|<|%|&|\\+|-|\\*|/|!|\\^|\\$|#|@|~|\\?|,|\\[|]|:|;|'|\\\\|\\|)");
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
}
