package cn.origincraft.magic.object;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class Spell {
    private SpellContext spellContext;
    private List<MagicWords> magicWordsList = new ArrayList<>();
    private MagicManager magicManager;


    public Spell(List<String> magicWordsStringList, MagicManager magicManager) {
        setMagicManager(magicManager);
        for (String s : magicWordsStringList) {
            magicWordsList.add(new MagicWords(s, magicManager));
        }
    }
    public Spell(String magicWordsString, MagicManager magicManager) {
        setMagicManager(magicManager);
        magicWordsList.add(new MagicWords(magicWordsString, magicManager));
    }

    public SpellContext execute(ContextMap contextMap) {
        spellContext = new SpellContext();
        spellContext.setSelf(this);
        spellContext.setContextMap(contextMap);
        spellContext.putMagicManager(getMagicManager());
        int index = 0;
        while (index < magicWordsList.size()) {
            // 中段判断
            if (spellContext.getExecuteBreak()) {
                spellContext.putExecuteBreak(false);
                break;
            }
            // 跳过判断
            if (spellContext.getExecuteContinue()) {
                spellContext.removeExecuteContinue();
                continue;
            }
            MagicWords magicWords = magicWordsList.get(index);
            // 初始化上下文中本次执行后要执行的序号
            spellContext.putExecuteNext(index + 1);
            // 执行次数计数器计数
            spellContext.addExecuteCount(1);
            // 记录本条序号为执行序号
            spellContext.putExecuteIndex(index);
            // 执行魔语并获取执行后的上下文
            try {
                magicWords.fastExecute(spellContext);
            } catch (Exception e) {
                // 获取异常的堆栈信息
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();

                // 将错误信息和堆栈信息一起放入 ErrorResult
                String errorMessage = e.getMessage();
                spellContext.putExecuteError(new ErrorResult("JAVA_ERROR", errorMessage + "\n" + stackTrace));
                break;
            }
            // 检查是否抛出错误
            if (spellContext.hasExecuteError()) {
                break;
            }
            // 记录执行语句为上一条语句
            spellContext.putExecutePrevious(index);
            // 语句下标与上下文同步
            index = spellContext.getExecuteNext();
            // 查看是否有跳过需求，有则处理
            if (spellContext.hasExecutePass()) {
                int passValue = spellContext.getExecutePass();
                if (passValue > 0) {
                    index += passValue;
                }
                spellContext.removeExecutePass();
            }
        }
        if (!spellContext.hasSpellReturn()) {
            spellContext.putSpellReturn(new NullResult());
        }
        return spellContext;
    }
    public SpellContext fastExecute(ContextMap contextMap) {
        spellContext = new SpellContext();
        spellContext.setSelf(this);
        spellContext.setContextMap(contextMap);
        spellContext.putMagicManager(getMagicManager());
        for (MagicWords magicWords : magicWordsList) {
            try {
                magicWords.fastExecute(spellContext);
            } catch (Exception e) {
                // 获取异常的堆栈信息
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();

                // 将错误信息和堆栈信息一起放入 ErrorResult
                String errorMessage = e.getMessage();
                spellContext.putExecuteError(new ErrorResult("JAVA_ERROR", errorMessage + "\n" + stackTrace));
                break;
            }
            // 检查是否抛出错误
            if (spellContext.hasExecuteError()) {
                break;
            }
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
        getMagicWordsList().add(new MagicWords(magicWords, getMagicManager()));
    }

    public MagicManager getMagicManager() {
        return magicManager;
    }

    public void setMagicManager(MagicManager magicManager) {
        this.magicManager = magicManager;
    }
}
