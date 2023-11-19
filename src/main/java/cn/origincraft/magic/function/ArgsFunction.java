package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.expression.functions.CallableFunction;
import cn.origincraft.magic.expression.functions.FunctionParameter;
import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.expression.functions.MagicResult;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.*;
import cn.origincraft.magic.utils.FunctionUtils;
import cn.origincraft.magic.utils.MethodUtils;

import java.util.*;

public abstract class ArgsFunction extends FormatFunction {
    private Map<Integer, ParseType> parseTypeMap = new HashMap<>();
    private ParseType defaultParseType = ParseType.ALL;

    /**
     * 当方法被调用时执行的代码
     *
     * @param spellContext 上下文
     * @param args         方法参数列表
     * @param argsSetting  参数设置
     * @return 方法返回值
     */
    public abstract FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting);

    public abstract List<ArgsSetting> getArgsSetting();

    /**
     * 当方法被调用时执行的代码
     *
     * @param parameter 参数
     * @return 方法返回值
     */
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        // 获取参数设置
        List<ArgsSetting> argsSettings = getArgsSetting();
        // 获取上下文
        SpellContext spellContext = MethodUtils.getSpellContext(parameter);
        // 获取参数
        List<FunctionResult> args = new ArrayList<>();
        // 获取参数字符串
        String para = spellContext.getExecuteParameter();
        // 获取魔法管理器
        MagicManager magicManager = spellContext.getMagicManager();
        // 解析参数
        List<Object> list = FunctionUtils
                .parseParaExpression(para, magicManager
                        .getFastExpression()
                        .getFunctionManager());
        // 遍历参数
        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            ParseType parseType = getParseType(i);
            // 选择解析方式
            switch (parseType) {
                // 全解析
                case ALL -> {
                    FunctionResult functionResult;
                    if (MethodUtils.isFunction(o)) {
                        functionResult = FunctionUtils.parseFunction(spellContext, o);
                    } else {
                        functionResult = FunctionUtils.parseVariable(spellContext, o);
                    }
                    if (functionResult instanceof ErrorResult) {
                        return functionResult;
                    }
                    args.add(functionResult);
                }
                // 只解析函数
                case ONLY_FUNCTION -> {
                    if (MethodUtils.isFunction(o)) {
                        FunctionResult functionResult = FunctionUtils.parseFunction(spellContext, o);
                        if (functionResult instanceof ErrorResult) {
                            return functionResult;
                        }
                        args.add(functionResult);
                    } else {
                        args.add(new StringResult((String) o));
                    }
                }
                // 只解析变量
                case ONLY_VARIABLE -> {
                    if (MethodUtils.isFunction(o)) {
                        CallableFunction function = (CallableFunction) o;
                        args.add(new StringResult(function.toString()));
                    } else {
                        FunctionResult functionResult = FunctionUtils.parseVariable(spellContext, o);
                        if (functionResult instanceof ErrorResult) {
                            return functionResult;
                        }
                        args.add(functionResult);
                    }
                }
                //全解析但是函数转换为魔咒
                case FUNCTION_TO_SPELL -> {
                    if (MethodUtils.isFunction(o)) {
                        CallableFunction function = (CallableFunction) o;
                        List<String> spellList = new ArrayList<>();
                        spellList.add(function.toString());
                        args.add(new SpellResult(new Spell(spellList, magicManager)));
                    } else {
                        FunctionResult functionResult = FunctionUtils.parseVariable(spellContext, o);
                        if (functionResult instanceof ErrorResult) {
                            return functionResult;
                        }
                        args.add(functionResult);
                    }
                }
                //只解析函数但是函数转换为魔咒
                case ONLY_FUNCTION_TO_SPELL -> {
                    if (MethodUtils.isFunction(o)) {
                        CallableFunction function = (CallableFunction) o;
                        List<String> spellList = new ArrayList<>();
                        spellList.add(function.toString());
                        args.add(new SpellResult(new Spell(spellList, magicManager)));
                    } else {
                        args.add(new StringResult((String) o));
                    }
                }
                case ORIGIN -> {
                    args.add(new StringResult((String) o));
                }
            }
        }
        // 遍历参数设置,寻找匹配的参数设置触发
        for (ArgsSetting argsSetting : argsSettings) {
            if (argsSetting.checkArgs(args)) {
                return whenFunctionCalled(spellContext, args, argsSetting);
            }
        }
        // 如果没有找到匹配的参数设置，返回错误信息

        // 获取输入类型
        StringBuilder inArgsTypes = new StringBuilder();
        for (FunctionResult arg : args) {
            inArgsTypes.append(((MagicResult) arg).getName()).append(" ");
        }
        // 生成错误信息
        ErrorResult errorResult = new ErrorResult("ARGS_ERROR", getName() + " insufficient parameters or type mismatch");
        // log生成
        errorResult.addLog("Your input:");
        errorResult.addLog(getName() + "( " + inArgsTypes + ")");
        errorResult.addLog("Possible usage:");
        errorResult.addLog(getUsage());
        return errorResult;
    }

    public List<String> getUsage() {
        List<String> usages = new ArrayList<>();
        List<ArgsSetting> argsSettings = getArgsSetting();
        for (ArgsSetting argsSetting : argsSettings) {
            usages.add(argsSetting.getId()+":");
            StringBuilder typeUsage = new StringBuilder();
            typeUsage.append(" ");
            for (String argsType : argsSetting.getArgsTypes()) {
                typeUsage.append(argsType).append(" ");
            }
            usages.add(" "+getName() + "( " + typeUsage + ")");
            usages.add(" Return Type:" + argsSetting.getResultType());
            usages.add(" Info:");
            usages.addAll(argsSetting.getInfo());
        }
        return usages;
    }

    public abstract String getType();

    public ParseType getDefaultParseType() {
        return defaultParseType;
    }

    public Map<Integer, ParseType> getParseTypeMap() {
        return parseTypeMap;
    }

    public void setParseTypeMap(Map<Integer, ParseType> parseTypeMap) {
        this.parseTypeMap = parseTypeMap;
    }

    public void setParseType(int index, ParseType parseType) {
        parseTypeMap.put(index, parseType);
    }

    public void setDefaultParseType(ParseType parseType) {
        defaultParseType = parseType;
    }

    public ParseType getParseType(int index) {
        if (parseTypeMap.containsKey(index)) {
            return parseTypeMap.get(index);
        } else {
            return defaultParseType;
        }
    }
}
