package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.expression.functions.FastFunction;
import cn.origincraft.magic.expression.functions.FunctionParameter;
import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.FunctionUtils;
import cn.origincraft.magic.utils.MethodUtil;

import java.util.List;


public abstract class OriginFunction extends FormatFunction {
    /**
     * 当方法被调用时执行的代码
     *
     * @param spellContext 上下文
     * @param args         方法参数列表
     * @return 方法返回值
     */
    public abstract FunctionResult whenFunctionCalled(SpellContext spellContext, List<Object> args);

    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        MagicManager mm = spellContext.getMagicManager();
        List<Object> list = FunctionUtils
                .parseParaExpression(para, mm
                        .getFastExpression()
                        .getFunctionManager());
        return whenFunctionCalled(spellContext, list);
    }

    public abstract String getType();
}
