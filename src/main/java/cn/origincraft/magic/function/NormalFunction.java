package cn.origincraft.magic.function;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.expression.functions.CallableFunction;
import cn.origincraft.magic.expression.functions.FastFunction;
import cn.origincraft.magic.expression.functions.FunctionParameter;
import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.expression.parameters.StringParameter;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.*;
import cn.origincraft.magic.utils.FunctionUtils;
import cn.origincraft.magic.utils.MethodUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class NormalFunction extends ArgsFunction implements FastFunction {
    /**
     * 当方法被调用时执行的代码
     *
     * @param spellContext 上下文
     * @param args         方法参数列表
     * @return 方法返回值
     */
    public abstract FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args);
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        return whenFunctionCalled(spellContext,args);
    }
    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings=new ArrayList<>();
        argsSettings.add(new ArgsSetting("A")
                .addArgType("...")
                .addInfo("any")
                .addInfo("not set info")
                .setResultType("Unknown")
        );
        return argsSettings;
    }
    public abstract String getType();
}
