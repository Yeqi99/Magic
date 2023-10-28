package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.expression.functions.CallableFunction;
import cn.origincraft.magic.expression.functions.FunctionParameter;
import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.expression.parameters.StringParameter;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.utils.FunctionUtils;
import cn.origincraft.magic.utils.MethodUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class HasVariableFunction extends NormalFunction {
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
                args.add(new StringResult((String) o));
            }
        }
        return whenFunctionCalled(spellContext,args);
    }

    public abstract String getType();
}
