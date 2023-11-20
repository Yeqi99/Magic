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
//        testList.add("vdef(a number(str( -105 )))");
        testList.add("wait(1 1000)");
        testList.add("print(str( -105 str(1)))");
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
