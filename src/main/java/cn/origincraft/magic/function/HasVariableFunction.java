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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
