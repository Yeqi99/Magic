import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.object.SpellContextResult;

import java.util.List;

public class VarFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContextParameter spellContextParameter= (SpellContextParameter) parameter;
        SpellContext spellContext=spellContextParameter.getSpellContext();
        String stringParameter=spellContext.getExecuteParameter();
        String[] p=stringParameter.split(" ");

        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "var";
    }

    @Override
    public String getType() {
        return "system";
    }
}
