package cn.origincraft.magic.function.system.variable;

import cn.origincraft.magic.interpreter.fastexpression.functions.CallableFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.interpreter.fastexpression.managers.FunctionManager;
import cn.origincraft.magic.interpreter.fastexpression.parameters.StringParameter;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;

import java.util.List;
public class GetVariableFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        FunctionManager fManager = spellContext
                .getMagicManager()
                .getFastExpression()
                .getFunctionManager();
        List<Object> os = fManager.parseParaExpression(para);

        if (os.isEmpty()) {
            return new FunctionResult.IntResult(0);
        }

        FunctionResult fResult = new FunctionResult.IntResult(0);
        Object value = os.get(0);

        if (MethodUtil.isFunction(value)) {
            CallableFunction callableFunction = (CallableFunction) value;
            StringParameter stringParameter =
                    (StringParameter) callableFunction.getParameter();
            spellContext.putExecuteParameter(stringParameter.getString());
            SpellContextResult spellContextResult =
                    (SpellContextResult) callableFunction
                            .getFunction()
                            .call(
                                    new SpellContextParameter(spellContext)
                            );
            spellContext = spellContextResult.getSpellContext();
            FunctionResult functionResult = spellContext.getExecuteReturn();

            if (functionResult instanceof FunctionResult.StringResult v) {
                String s = v.getString();
                if (spellContext.getVariableMap().containsKey(s)) {
                    spellContext.putExecuteReturn(new FunctionResult.ObjectResult(spellContext.getVariableMap().get(s)));
                    return new SpellContextResult(spellContext);
                }
            }

            // ...需要处理的结果
        } else if (value instanceof String) {
            String variableName = (String) value;
            if (spellContext.getVariableMap().containsKey(variableName)) {
                Object variableValue = spellContext.getVariableMap().get(variableName);
                spellContext.putExecuteReturn(new FunctionResult.ObjectResult(variableValue));
                return new SpellContextResult(spellContext);
            }
        }

        spellContext.putExecuteReturn(new FunctionResult.DefaultResult(FunctionResult.Status.FAILURE));
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "getVariable";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
