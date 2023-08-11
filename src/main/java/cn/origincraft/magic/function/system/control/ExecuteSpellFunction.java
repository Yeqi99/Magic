package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.interpreter.fastexpression.functions.CallableFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.interpreter.fastexpression.managers.FunctionManager;
import cn.origincraft.magic.interpreter.fastexpression.parameters.StringParameter;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;

import java.util.List;

public class ExecuteSpellFunction implements FastFunction {
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
            return new FunctionResult.BooleanResult(false);
        }

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

            if (functionResult instanceof FunctionResult.SpellResult v) {
                Spell spell=v.getSpell();
                spellContext = spell.execute(spellContext.getContextMap());
            }

            if (functionResult instanceof FunctionResult.ObjectResult v) {
                if (v.getObject() instanceof Spell spell) {
                    spellContext = spell.execute(spellContext.getContextMap());
                }
            }
            // ...需要处理的结果
        } else {
            if (spellContext
                    .getObjectMap()
                    .containsKey((String) value)) {
                Object v = spellContext.getObjectMap().get((String) value);
                if (v instanceof Spell spell) {
                    spellContext = spell.execute(spellContext.getContextMap());
                }
            }
        }
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "executeSpell";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
