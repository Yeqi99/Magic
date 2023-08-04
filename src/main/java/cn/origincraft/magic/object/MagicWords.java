package cn.origincraft.magic.object;


import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.interpreter.fastexpression.FastExpression;
import cn.origincraft.magic.interpreter.fastexpression.functions.CallableFunction;
import cn.origincraft.magic.interpreter.fastexpression.parameters.StringParameter;
import cn.origincraft.magic.utils.MethodUtil;

import java.util.List;

public class MagicWords {
    private String originMagicWords;
    private List<CallableFunction> function;
    private MagicManager magicManager;

    public MagicWords(String magicWords,MagicManager magicManager) {
        setOriginMagicWords(magicWords);
        setMagicManager(magicManager);
        // 解析语句为方法
        List<CallableFunction> function = magicManager
                .getFastExpression()
                .getFunctionManager()
                .parseExpression(magicWords);
        // 方法按照优先级排序
        function= MethodUtil
                .sortFunctions(
                        magicManager
                                .getTypePriority(),function);
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

    public MagicManager getMagicManager() {
        return magicManager;
    }

    public void setMagicManager(MagicManager magicManager) {
        this.magicManager = magicManager;
    }
}
