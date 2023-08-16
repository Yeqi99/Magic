package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;
import dev.rgbmc.expression.functions.CallableFunction;
import dev.rgbmc.expression.functions.FastFunction;
import dev.rgbmc.expression.functions.FunctionParameter;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.parameters.StringParameter;
import java.util.List;

public class ForFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        MagicManager mm = spellContext.getMagicManager();
        List<Object> list = mm
                .getFastExpression()
                .getFunctionManager()
                .parseParaExpression(para);
        if (list.size() < 2) {
            return new SpellContextResult(spellContext);
        }

        while (true) {
            Object condition = list.get(0);
            if (MethodUtil.isFunction(condition)) {
                CallableFunction conditionFunction = (CallableFunction) condition;
                StringParameter conditionParameter = (StringParameter) conditionFunction.getParameter();
                spellContext.putExecuteParameter(conditionParameter.getString());
                SpellContextResult conditionResult =
                        (SpellContextResult) conditionFunction.getFunction().call(new SpellContextParameter(spellContext));
                FunctionResult functionResult = conditionResult.getSpellContext().getExecuteReturn();
                if (functionResult instanceof FunctionResult.BooleanResult v) {
                    if (!v.getBoolean()) {
                        break;
                    }
                }
            } else {
                break;
            }

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

        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "for";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
