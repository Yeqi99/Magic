package cn.origincraft.magic.function.system.thread;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.SpellResult;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.IntegerResult;

import java.util.List;

public class SpellAsyncExecuteFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()){
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "SpellExecute function requires at least one argument.");
        }
        int count = 0;
        for (FunctionResult arg : args) {
            if (arg instanceof SpellResult spellResult){
                Spell spell = spellResult.getSpell();
                final SpellContext clone=spellContext;
                Thread asyncThread = new Thread(() -> {
                    spell.execute(clone.getContextMap());
                });
                asyncThread.start();
                try {
                    asyncThread.join();
                } catch (InterruptedException e) {
                    return new ErrorResult("ERROR_IN_THREAD", "Thread interrupted.");
                }
                count++;
            }
        }
        // 返回执行统计
        return new IntegerResult(count);
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "spellAsyncExecute";
    }
}
