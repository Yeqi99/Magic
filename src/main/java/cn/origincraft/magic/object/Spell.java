package cn.origincraft.magic.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Spell {
    private SpellContext spellContext;
    private List<MagicWords> magicWordsList = new ArrayList<>();

    public Spell(List<String> magicWordsStringList) {
        for (String s : magicWordsStringList) {
            magicWordsList.add(new MagicWords(s));
        }
    }
    public SpellContext execute(Map<String, Object> objectMap,Map<String, Object> variableMap) {
        spellContext = new SpellContext();
        // 对象字典初始化
        spellContext.getObjectMap().putAll(objectMap);
        // 变量字典初始化
        spellContext.getVariableMap().putAll(variableMap);
        int index = 0;
        while (index < magicWordsList.size()) {
            // 中段判断
            if (spellContext.getExecuteBreak()){
                break;
            }
            // 跳过判断
            if (spellContext.getExecuteContinue()){
                spellContext.removeExecuteContinue();
                continue;
            }
            MagicWords magicWords=magicWordsList.get(index);
            // 初始化上下文中本次执行后要执行的序号
            spellContext.putExecuteNext(index+1);
            // 执行次数计数器计数
            spellContext.addExecuteCount(1);
            // 记录本条序号为执行序号
            spellContext.putExecuteIndex(index);
            // 执行魔语并获取执行后的上下文
            spellContext = magicWords.execute(spellContext);
            // 记录执行语句为上一条语句
            spellContext.putExecutePrevious(index);
            // 查看是否有跳过需求，有则处理
            if (spellContext.hasExecutePass()){
                int passValue=spellContext.getExecutePass();
                if (passValue>0){
                    index += passValue;
                }
                spellContext.removeExecutePass();
            }
            // 语句下标与上下文同步
            index = spellContext.getExecuteNext();
        }
        return spellContext;
    }
    public SpellContext getSpellContext() {
        return spellContext;
    }

    public void setSpellContext(SpellContext spellContext) {
        this.spellContext = spellContext;
    }

    public List<MagicWords> getMagicWordsList() {
        return magicWordsList;
    }

    public void setMagicWordsList(List<MagicWords> magicWordsList) {
        this.magicWordsList = magicWordsList;
    }

    public void addMagicWords(String magicWords) {
        getMagicWordsList().add(new MagicWords(magicWords));
    }
}
