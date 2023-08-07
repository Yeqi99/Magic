package cn.origincraft.magic.function.system.variable;

import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.interpreter.fastexpression.functions.CallableFunction;
import cn.origincraft.magic.interpreter.fastexpression.managers.FunctionManager;
import cn.origincraft.magic.interpreter.fastexpression.parameters.StringParameter;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        FunctionManager fManager = spellContext
                .getMagicManager()
                .getFastExpression()
                .getFunctionManager();
        List<Object> os = fManager.parseParaExpression(para);

        Set<Object> resultSet = new HashSet<>();

        for (Object value : os) {
            if (MethodUtil.isFunction(value)) {
                CallableFunction callableFunction = (CallableFunction) value;
                StringParameter stringParameter = (StringParameter) callableFunction.getParameter();
                spellContext.putExecuteParameter(stringParameter.getString());
                SpellContextParameter spellContextParameter = new SpellContextParameter(spellContext);
                SpellContextResult spellContextResult = (SpellContextResult) callableFunction.getFunction().call(spellContextParameter);
                spellContext = spellContextResult.getSpellContext();
                FunctionResult functionResult = spellContextResult.getSpellContext().getExecuteReturn();
                Object resultValue = extractFunctionResultValue(functionResult);
                resultSet.add(resultValue);
            } else if (spellContext.getVariableMap().containsKey(value.toString())) {
                Object v = spellContext.getVariableMap().get(value.toString());
                resultSet.add(v);
            } else {
                resultSet.add(value);
            }
        }

        spellContext.putExecuteReturn(new FunctionResult.SetResult(resultSet));
        return new SpellContextResult(spellContext);
    }

    private Object extractFunctionResultValue(FunctionResult functionResult) {
        if (functionResult instanceof FunctionResult.ListResult) {
            return ((FunctionResult.ListResult) functionResult).getList();
        } else if (functionResult instanceof FunctionResult.ObjectResult) {
            return ((FunctionResult.ObjectResult) functionResult).getObject();
        } else if (functionResult instanceof FunctionResult.StringResult) {
            return ((FunctionResult.StringResult) functionResult).getString();
        } else if (functionResult instanceof FunctionResult.IntResult) {
            return ((FunctionResult.IntResult) functionResult).getInt();
        } else if (functionResult instanceof FunctionResult.DoubleResult) {
            return ((FunctionResult.DoubleResult) functionResult).getDouble();
        } else if (functionResult instanceof FunctionResult.BooleanResult) {
            return ((FunctionResult.BooleanResult) functionResult).getBoolean();
        } else if (functionResult instanceof FunctionResult.DefaultResult v) {
            if (v.getStatus() == FunctionResult.Status.SUCCESS) {
                return true;
            } else if (v.getStatus() == FunctionResult.Status.FAILURE) {
                return false;
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}