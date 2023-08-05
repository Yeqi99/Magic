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

import java.util.List;
// 生成字符串值
public class StringFunction implements FastFunction {
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
            return new FunctionResult.StringResult("");
        }

        FunctionResult fResult = new FunctionResult.StringResult("");
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

            if (functionResult instanceof FunctionResult.StringResult) {
                fResult = functionResult;
            }

            if (functionResult instanceof FunctionResult.DoubleResult v) {
                fResult = new FunctionResult.StringResult(String.valueOf(v.getDouble()));
            }

            if (functionResult instanceof FunctionResult.IntResult v) {
                fResult = new FunctionResult.StringResult(String.valueOf(v.getInt()));
            }

            if (functionResult instanceof FunctionResult.BooleanResult v) {
                fResult = new FunctionResult.StringResult(String.valueOf(v.getBoolean()));
            }

            if (functionResult instanceof FunctionResult.ObjectResult v) {
                fResult = new FunctionResult.StringResult(String.valueOf(v.getObject()));
            }
            // ...需要处理的结果
        } else {
            if (spellContext
                    .getVariableMap()
                    .containsKey((String) value)) {
                Object v = spellContext.getVariableMap().get((String) value);
                fResult = new FunctionResult.StringResult(String.valueOf(v));
            } else {
                fResult = new FunctionResult.StringResult(String.valueOf(value));
            }
        }

        spellContext.putExecuteReturn(fResult);
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "string";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}