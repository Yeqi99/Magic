import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.FunctionRegister;
import cn.origincraft.magic.object.NormalContext;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;


public class Test {

    private static final NormalContext contextMap = new NormalContext();
    public static void main(String[] args) {
        MagicManager magicManager = new MagicManager();
        FunctionRegister.regDefault(magicManager);
        List<String> testList = new ArrayList<>();
        testList.add("print(str(a b c c))");
//        testList.add("vdef(i int(0)) vdef(j int(10))");
//        testList.add("vdef(message str(Hello space() World))");
//        testList.add("while(comp(i < j)) print(第 i 次 message) vdef(i add(i 1))");
//        testList.add("vdef(redisPath str(redis://182208@192.168.100.131:6379))");
//        testList.add("print(redisPath)");
        Spell spell = new Spell(testList, magicManager);
        SpellContext spellContext= spell.execute(contextMap);
        if(spellContext.hasExecuteError()){
            System.out.println(spellContext.getExecuteError().getErrorId()+":"+spellContext.getExecuteError().getInfo());
            for (String s : spellContext.getExecuteError().getLog()) {
                System.out.println(s);

            }
            for (String s : spellContext.getExecuteErrorLocation()) {
                System.out.println(s);
            }

        }

    }

}
