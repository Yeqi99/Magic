import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.object.SpellContextResult;

public class TestFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        System.out.print("123321");
        SpellContextParameter spellContextParameter= (SpellContextParameter) parameter;
        return new SpellContextResult(spellContextParameter.getSpellContext());
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getType() {
        return "system";
    }
}
