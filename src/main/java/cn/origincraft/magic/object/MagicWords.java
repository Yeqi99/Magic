package cn.origincraft.magic.object;


import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.interpreter.fastexpression.FastExpression;
import cn.origincraft.magic.interpreter.fastexpression.functions.CallableFunction;
import cn.origincraft.magic.interpreter.fastexpression.parameters.StringParameter;

import java.util.List;

public class MagicWords {
    private String originMagicWords;
    private List<CallableFunction> function;

    public MagicWords(String magicWords) {
        setOriginMagicWords(magicWords);
        FastExpression fastExpression = MagicManager.getFastExpression();
        List<CallableFunction> function = fastExpression.getFunctionManager().parseExpression(magicWords);
        setFunction(function);
    }

    public SpellContext execute(SpellContext spellContext) {
        for (CallableFunction callableFunction : function) {
            StringParameter stringParameter= (StringParameter) callableFunction.getParameter();
            spellContext.putExecuteParameter(stringParameter.getString());
            SpellContextResult spellContextResult =
                    (SpellContextResult) callableFunction.getFunction()
                            .call(new SpellContextParameter(spellContext));
            spellContext=spellContextResult.getSpellContext();
        }
        return spellContext;
    }
    public List<CallableFunction> getFunction() {
        return function;
    }

    public void setFunction(List<CallableFunction> function) {
        this.function = function;
    }


    public String getOriginMagicWords() {
        return originMagicWords;
    }

    public void setOriginMagicWords(String originMagicWords) {
        this.originMagicWords = originMagicWords;
    }
}
