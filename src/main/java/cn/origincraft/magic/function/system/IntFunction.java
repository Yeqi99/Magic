package cn.origincraft.magic.function.system;

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
import cn.origincraft.magic.utils.VariableUtil;

import java.util.List;

// Int值生成
public class IntFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        FunctionManager fManager = spellContext
                .getMagicManager()
                .getFastExpression()
                .getFunctionManager();
        List<Object> os = fManager.parseParaExpression(para);

        if (os.size() < 1) {
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

            if (functionResult instanceof FunctionResult.IntResult) {
                fResult = functionResult;
            }

            if (functionResult instanceof FunctionResult.DoubleResult v) {
                fResult = new FunctionResult.IntResult((int) v.getDouble());
            }

            if (functionResult instanceof FunctionResult.BooleanResult v) {
                fResult = new FunctionResult.IntResult(v.getBoolean() ? 1 : 0);
            }

            if (functionResult instanceof FunctionResult.ObjectResult v) {
                if (VariableUtil.isInt(v.getObject())) {
                    fResult = new FunctionResult.IntResult((int) v.getObject());
                }
                if (VariableUtil.isBoolean(v.getObject())) {
                    fResult = new FunctionResult.IntResult((boolean) v.getObject() ? 1 : 0);
                }
            }
            // ...需要处理的结果
        } else {
            if (spellContext
                    .getVariableMap()
                    .containsKey((String) value)) {
                Object v = spellContext.getVariableMap().get((String) value);
                if (VariableUtil.isInt(v)) {
                    fResult = new FunctionResult.IntResult((int) v);
                }
                if (VariableUtil.isBoolean(v)) {
                    fResult = new FunctionResult.IntResult((boolean) v ? 1 : 0);
                }
            } else {
                if (VariableUtil.tryInt((String) value)) {
                    fResult = new FunctionResult.IntResult(Integer.parseInt((String) value));
                }
            }
        }

        spellContext.putExecuteReturn(fResult);
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "int";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
