package cn.origincraft.magic.function.system.control;

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

import java.util.List;

public class IfFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        MagicManager mm = spellContext.getMagicManager();
        List<Object> list = mm
                .getFastExpression()
                .getFunctionManager()
                .parseParaExpression(para);
        if (list.size() < 1) {
            return new SpellContextResult(spellContext);
        }

        Object condition = list.get(0);
        boolean result = false;

        if (MethodUtil.isFunction(condition)) {
            CallableFunction conditionFunction = (CallableFunction) condition;
            StringParameter conditionParameter = (StringParameter) conditionFunction.getParameter();
            spellContext.putExecuteParameter(conditionParameter.getString());
            SpellContextResult conditionResult =
                    (SpellContextResult) conditionFunction.getFunction().call(new SpellContextParameter(spellContext));
            FunctionResult functionResult = conditionResult.getSpellContext().getExecuteReturn();
            if (functionResult instanceof FunctionResult.BooleanResult v) {
                result = v.getBoolean();
            }
        }

        if (result) {
            for (int i = 1; i < list.size(); i++) {
                Object o = list.get(i);
                if (MethodUtil.isFunction(o)) {
                    CallableFunction function = (CallableFunction) o;
                    StringParameter stringParameter = (StringParameter) function.getParameter();
                    spellContext.putExecuteParameter(stringParameter.getString());
                    function.getFunction().call(new SpellContextParameter(spellContext));
                }
            }
        }

        spellContext.putExecuteReturn(new FunctionResult.BooleanResult(result));

        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "if";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
