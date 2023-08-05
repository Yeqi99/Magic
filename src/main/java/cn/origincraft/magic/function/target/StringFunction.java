package cn.origincraft.magic.function.target;

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

public class StringFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        String[] s = para.split(" ",2);
        if (s.length < 2){
            return new SpellContextResult(spellContext);
        }
        String variableName = s[0];
        String value = s[1];
        String result = "";
        List<CallableFunction> list = spellContext
                .getMagicManager()
                .getFastExpression()
                .getFunctionManager()
                .parseExpression(value);
        if (list.size() > 0){
            StringParameter stringParameter = (StringParameter) list.get(0).getParameter();
            spellContext.putExecuteParameter(stringParameter.getString());
            FunctionResult functionResult = list.get(0).getFunction().call(new SpellContextParameter(spellContext));
            if (functionResult instanceof FunctionResult.StringResult){
                result = ((FunctionResult.StringResult) functionResult).getString();
            }
        }else {
            if(spellContext.getVariableMap().containsKey(value)){
                result = String.valueOf(spellContext.getVariableMap().get(value));
                spellContext.putVariable(variableName, result);
                return new SpellContextResult(spellContext);
            }
            result =value;
        }
        spellContext.putVariable(variableName, result);
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "string";
    }

    @Override
    public String getType() {
        return "TARGET";
    }
}