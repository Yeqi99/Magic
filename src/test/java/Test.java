import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.FunctionRegister;
import cn.origincraft.magic.function.system.io.output.PrintFunction;
import cn.origincraft.magic.object.ContextMap;
import cn.origincraft.magic.object.NormalContext;
import cn.origincraft.magic.object.Spell;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class Test {

    private static final NormalContext contextMap = new NormalContext();
    public static void main(String[] args) {
        MagicManager magicManager = new MagicManager();
        FunctionRegister.regDefault(magicManager);
        List<String> testList = new ArrayList<>();

        testList.add("vdef(i int(0)) vdef(j int(10))");
        testList.add("vdef(message str(Hello space() World))");
        testList.add("while(comp(i < j)) vdef(i add(i 1)) print(第 i 次 message)");
        Spell spell = new Spell(testList, magicManager);

        spell.execute(contextMap);

    }

}
