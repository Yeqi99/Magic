package cn.origincraft.magic.function.system.thread;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.IntegerResult;
import cn.origincraft.magic.function.results.SpellResult;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class SpellAsyncExecuteFunction extends ArgsFunction {

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        int count = 0;
        for (FunctionResult arg : args) {
            if (arg instanceof SpellResult spellResult) {
                Spell spell = spellResult.getSpell();
                final SpellContext clone = spellContext;
                Thread asyncThread = new Thread(() -> spell.execute(clone.getContextMap()));
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
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings=new ArrayList<>();
        argsSettings.add(new ArgsSetting("A")
                .addArgType("...")
                .addInfo("spell...")
                .addInfo("Execute spell by async and deamon")
                .setResultType("Integer")
        );
        return argsSettings;
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
