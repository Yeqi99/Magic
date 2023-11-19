package cn.origincraft.magic.function;

import cn.origincraft.magic.expression.functions.FunctionParameter;
import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.MethodUtils;


public abstract class OnlyStringFunction extends FormatFunction {
    /**
     * 当方法被调用时执行的代码
     *
     * @param spellContext 上下文
     * @param args         方法参数列表
     * @return 方法返回值
     */
    public abstract FunctionResult whenFunctionCalled(SpellContext spellContext, String args);

    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtils.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        return whenFunctionCalled(spellContext, para);
    }
    public abstract String getType();
}
