package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.expression.functions.*;
import cn.origincraft.magic.expression.parameters.StringParameter;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.*;
import cn.origincraft.magic.utils.FunctionUtils;
import cn.origincraft.magic.utils.MethodUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ArgsFunction implements FastFunction {
    public abstract FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting);

    public abstract List<ArgsSetting> getArgsSetting();

    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        List<FunctionResult> args = new ArrayList<>();
        String para = spellContext.getExecuteParameter();
        MagicManager mm = spellContext.getMagicManager();
        List<Object> list = FunctionUtils
                .parseParaExpression(para, mm
                        .getFastExpression()
                        .getFunctionManager());
        for (Object o : list) {
            if (MethodUtil.isFunction(o)) {
                // 设置当前方法为嵌套调用
                CallableFunction function = (CallableFunction) o;
                StringParameter stringParameter =
                        (StringParameter) function.getParameter();
                spellContext.putExecuteParameter(stringParameter.getString());
                FunctionResult functionResult = function.getFunction().call(
                        new SpellContextParameter(spellContext)
                );
                if (functionResult instanceof ErrorResult) {
                    return functionResult;
                }
                args.add(functionResult);
            } else {
                String value = (String) o;
                if (spellContext
                        .getContextMap()
                        .hasVariable(value)) {
                    Object v = spellContext.getContextMap().getVariable(value);

                    if (v instanceof ErrorResult) {
                        return (ErrorResult) v;
                    }
                    if (v instanceof Integer) {
                        args.add(new IntegerResult((Integer) v));
                    } else if (v instanceof Double) {
                        args.add(new DoubleResult((Double) v));
                    } else if (v instanceof String) {
                        args.add(new StringResult((String) v));
                    } else if (v instanceof List) {
                        args.add(new ListResult((List<?>) v));
                    } else if (v instanceof Map) {
                        args.add(new MapResult((Map<?, ?>) v));
                    } else if (v instanceof Set) {
                        args.add(new SetResult((Set<?>) v));
                    } else if (v instanceof SpellContext) {
                        args.add(new SpellContextResult((SpellContext) v));
                    } else if (v instanceof Spell) {
                        args.add((new SpellResult((Spell) v)));
                    } else if (v instanceof MagicWords) {
                        args.add(new MagicWordsResult((MagicWords) v));
                    } else if (v instanceof Boolean) {
                        args.add(new BooleanResult((Boolean) v));
                    } else if (v instanceof Long) {
                        args.add((new LongResult((Long) v)));
                    } else if (v instanceof ContextMapResult) {
                        args.add((ContextMapResult) v);
                    } else if (v instanceof Float) {
                        args.add((new FloatResult((Float) v)));
                    } else if (v instanceof ArgumentsResult) {
                        args.add((ArgumentsResult) v);
                    } else if (v instanceof FunctionResult) {
                        args.add((FunctionResult) v);
                    } else {
                        args.add(new ObjectResult(v));
                    }
                } else {
                    args.add(new StringResult(value));
                }
            }
        }
        // 获得语义的所有参数设置
        List<ArgsSetting> argsSettings = getArgsSetting();
        for (ArgsSetting argsSetting : argsSettings) {
            if (argsSetting.checkArgs(args)) {
                return whenFunctionCalled(spellContext, args, argsSetting);
            }
        }
        StringBuilder inArgsTypes = new StringBuilder();
        for (FunctionResult arg : args) {
            inArgsTypes.append(((MagicResult) arg).getName()).append(" ");
        }
        // 如果没有找到匹配的参数设置，返回错误信息
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
            StringBuilder typeUsage = new StringBuilder();
            for (String argsType : argsSetting.getArgsTypes()) {
                typeUsage.append(argsType).append(" ");
            }
            usages.add(getName() + "( " + typeUsage + ")");
            usages.add("Return Type:" + argsSetting.getResultType());
            usages.add("Info:");
            usages.addAll(argsSetting.getInfo());
        }
        return usages;
    }

    public abstract String getType();
}
