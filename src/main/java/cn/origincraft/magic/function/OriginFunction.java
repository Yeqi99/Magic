package cn.origincraft.magic.function;

import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.MethodUtil;
import dev.rgbmc.expression.functions.FastFunction;
import dev.rgbmc.expression.functions.FunctionParameter;
import dev.rgbmc.expression.functions.FunctionResult;
import java.util.ArrayList;
import java.util.List;

public abstract class OriginFunction implements FastFunction {
    /**
     * 当方法被调用时执行的代码
     * @param spellContext 上下文
     * @param args 方法参数列表
     * @return 方法返回值
     */
    public abstract FunctionResult whenFunctionCalled(SpellContext spellContext, List<String> args);

    @Override
    public FunctionResult call(FunctionParameter parameter) {
        List<String> args= new ArrayList<>();
        SpellContext spellContext= MethodUtil.getSpellContext(parameter);
        String para=spellContext.getExecuteParameter();


        return whenFunctionCalled(spellContext, args);
    }

    public abstract String getType();
}
