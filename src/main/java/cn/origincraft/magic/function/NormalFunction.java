package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.*;
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
                // 设置当前方法为嵌套调用
                CallableFunction function= (CallableFunction) o;
                StringParameter stringParameter=
                        (StringParameter)function.getParameter();
                spellContext.putExecuteParameter(stringParameter.getString());
                FunctionResult functionResult = function.getFunction().call(
                        new SpellContextParameter(spellContext)
                );
                if (functionResult instanceof ErrorResult){
                    return functionResult;
                }
                args.add(functionResult);
            }else {
                String value= (String) o;
                if (spellContext
                        .getContextMap()
                        .hasVariable(value)) {
                    Object v = spellContext.getContextMap().getVariable(value);

                    if (v instanceof ErrorResult){
                        return (ErrorResult) v;
                    }
                    if (v instanceof Integer){
                        args.add(new IntegerResult((Integer) v));
                    }else if (v instanceof Double){
                        args.add(new DoubleResult((Double) v));
                    }else if (v instanceof String){
                        args.add(new StringResult((String) v));
                    }else if (v instanceof List){
                        args.add(new ListResult((List<?>) v));
                    }else if (v instanceof Map){
                        args.add(new MapResult((Map<?,?>) v));
                    }else if (v instanceof Set){
                        args.add(new SetResult((Set<?>) v));
                    }else if (v instanceof SpellContext){
                        args.add(new SpellContextResult((SpellContext) v));
                    }else if (v instanceof Spell){
                        args.add((new SpellResult((Spell) v)));
                    }else if (v instanceof MagicWords){
                        args.add(new MagicWordsResult((MagicWords) v));
                    }else if (v instanceof Boolean){
                        args.add(new BooleanResult((Boolean) v));
                    }else if(v instanceof Long){
                        args.add((new LongResult((Long) v)));
                    }else if(v instanceof ContextMapResult){
                        args.add((ContextMapResult) v);
                    }else if(v instanceof Float){
                        args.add((new FloatResult((Float) v)));
                    }else if(v instanceof ArgumentsResult){
                        args.add((ArgumentsResult) v);
                    }else if(v instanceof FunctionResult){
                        args.add((FunctionResult) v);
                    }else{
                        args.add(new ObjectResult(v));
                    }
                } else {
                    args.add(new StringResult(value));
                }
            }
        }
        return whenFunctionCalled(spellContext,args);
    }

    public abstract String getType();
}
