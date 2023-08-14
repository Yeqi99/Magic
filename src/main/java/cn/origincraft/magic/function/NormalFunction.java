package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.interpreter.fastexpression.functions.CallableFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.interpreter.fastexpression.parameters.StringParameter;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;

import java.util.*;

public abstract class NormalFunction implements FastFunction {
    abstract FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args);

    @Override
    public FunctionResult call(FunctionParameter parameter) {
        List<FunctionResult> args= new ArrayList<>();
        SpellContext spellContext= MethodUtil.getSpellContext(parameter);
        String para=spellContext.getExecuteParameter();
        MagicManager mm=spellContext.getMagicManager();
        List<Object> list=mm
                .getFastExpression()
                .getFunctionManager()
                .parseParaExpression(para);
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
                        args.add(new FunctionResult.IntResult((Integer) v));
                    }else if (v instanceof Double){
                        args.add(new FunctionResult.DoubleResult((Double) v));
                    }else if (v instanceof String){
                        args.add(new FunctionResult.StringResult((String) v));
                    }else if (v instanceof List){
                        args.add(new FunctionResult.ListResult((List<Object>) v));
                    }else if (v instanceof Map){
                        args.add(new FunctionResult.MapResult((Map<String,Object>) v));
                    }else if (v instanceof Set){
                        args.add(new FunctionResult.SetResult((Set<Object>) v));
                    }else if (v instanceof SpellContextResult){
                        args.add((SpellContextResult) v);
                    }else if (v instanceof FunctionResult.SpellResult){
                        args.add((FunctionResult.SpellResult) v);
                    }else if (v instanceof FunctionResult.MagicWordsResult){
                        args.add((FunctionResult.MagicWordsResult) v);
                    }else if (v instanceof FunctionResult.BooleanResult){
                        args.add((FunctionResult.BooleanResult) v);
                    }else if (v instanceof FunctionResult.DefaultResult){
                        args.add((FunctionResult.DefaultResult) v);
                    }else if (v instanceof FunctionResult.ObjectResult){
                        args.add((FunctionResult.ObjectResult) v);
                    }
                } else {
                    args.add(new FunctionResult.StringResult(value));
                }
            }
        }
        spellContext.putExecuteReturn(whenFunctionCalled(spellContext,args));
        return new SpellContextResult(spellContext);
    }

}
