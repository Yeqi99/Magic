package cn.origincraft.magic.object;


import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.utils.MethodUtil;
import dev.rgbmc.expression.functions.CallableFunction;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.parameters.StringParameter;

import java.util.ArrayList;
import java.util.List;

public class MagicWords {
    private String originMagicWords;
    private List<CallableFunction> function;
    private MagicManager magicManager;

    public MagicWords(String magicWords, MagicManager magicManager) {
        setOriginMagicWords(magicWords);
        setMagicManager(magicManager);
        // 解析语句为方法
        List<CallableFunction> function = magicManager
                .getFastExpression()
                .getFunctionManager()
                .parseExpression(magicWords);
        // 方法按照优先级排序
        function = MethodUtil
                .sortFunctions(
                        magicManager
                                .getTypePriority(), function);
        setFunction(function);
    }

    public SpellContext execute(SpellContext spellContext) {
        // 遍历魔语中的语义元素
        for (CallableFunction callableFunction : function) {
            // 设置当前方法为非嵌套调用
            StringParameter stringParameter = (StringParameter) callableFunction.getParameter();
            spellContext.putExecuteParameter(stringParameter.getString());

            FunctionResult result = callableFunction.getFunction()
                            .call(new SpellContextParameter(spellContext));
            if (result instanceof ErrorResult) {
                spellContext.putExecuteError(result);
                List<String> errLocation=new ArrayList<>();
                String location="At index "+spellContext.getExecuteIndex();
                String sum="Already executed "+spellContext.getExecuteCount()+" statements";
                String word="Words "+getOriginMagicWords();
                errLocation.add(location);
                errLocation.add(sum);
                errLocation.add(word);
                spellContext.putExecuteErrorLocation(errLocation);
                break;
            }
            // 检查是否继续这行本条魔语
            if (!spellContext.getExecuteIndexAllow(spellContext.getExecuteIndex())) {
                spellContext.removeExecuteIndexAllow(spellContext.getExecuteIndex());
                break;
            }
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
