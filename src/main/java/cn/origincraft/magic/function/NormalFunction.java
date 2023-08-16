package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.FunctionUtils;
import cn.origincraft.magic.utils.MethodUtil;
import dev.rgbmc.expression.functions.CallableFunction;
import dev.rgbmc.expression.functions.FastFunction;
import dev.rgbmc.expression.functions.FunctionParameter;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.parameters.StringParameter;
import dev.rgbmc.expression.results.*;
import java.util.*;

public abstract class NormalFunction implements FastFunction {
    /**
     * 当方法被调用时执行的代码
     * @param spellContext 上下文
     * @param args 方法参数列表
     * @return 方法返回值
     */
    public abstract FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args);

    @Override
    public FunctionResult call(FunctionParameter parameter) {
        List<FunctionResult> args= new ArrayList<>();
        SpellContext spellContext= MethodUtil.getSpellContext(parameter);
        String para=spellContext.getExecuteParameter();
        MagicManager mm=spellContext.getMagicManager();
        List<Object> list= FunctionUtils
                .parseParaExpression(para, mm
                        .getFastExpression()
                        .getFunctionManager());
        for (Object o : list) {
            if (MethodUtil.isFunction(o)){
                CallableFunction function= (CallableFunction) o;
                StringParameter stringParameter=
                        (StringParameter)function.getParameter();
                spellContext.putExecuteParameter(stringParameter.getString());
                SpellContextResult spellContextResult =
                        (SpellContextResult) function
                                .getFunction()
                                .call(
                                        new SpellContextParameter(spellContext)
                                );
                spellContext = spellContextResult.getSpellContext();
                FunctionResult functionResult = spellContext.getExecuteReturn();
                args.add(functionResult);
            }else {
                String value= (String) o;
                if (spellContext
                        .getVariableMap()
                        .containsKey(value)) {
                    Object v = spellContext.getVariableMap().get(value);

                    if (v instanceof Integer){
                        args.add(new IntegerResult((Integer) v));
                    }else if (v instanceof Double){
                        args.add(new DoubleResult((Double) v));
                    }else if (v instanceof String){
                        args.add(new StringResult((String) v));
                    }else if (v instanceof List){
                        args.add(new ListResult((List<Object>) v));
                    }else if (v instanceof Map){
                        args.add(new MapResult((Map<String,Object>) v));
                    }else if (v instanceof Set){
                        args.add(new SetResult((Set<Object>) v));
                    }else if (v instanceof SpellContextResult){
                        args.add((SpellContextResult) v);
                    }else if (v instanceof SpellResult){
                        args.add((SpellResult) v);
                    }else if (v instanceof MagicWordsResult){
                        args.add((MagicWordsResult) v);
                    }else if (v instanceof BooleanResult){
                        args.add((BooleanResult) v);
                    }else if (v instanceof FunctionResult.DefaultResult){
                        args.add((FunctionResult.DefaultResult) v);
                    }else if (v instanceof ObjectResult){
                        args.add((ObjectResult) v);
                    }
                } else {
                    args.add(new StringResult(value));
                }
            }
        }
        spellContext.putExecuteReturn(whenFunctionCalled(spellContext,args));
        return new SpellContextResult(spellContext);
    }

    public abstract String getType();
}
