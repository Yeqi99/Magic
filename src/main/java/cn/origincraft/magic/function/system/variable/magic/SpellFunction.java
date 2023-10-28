package cn.origincraft.magic.function.system.variable.magic;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ObjectResult;
import cn.origincraft.magic.function.results.SpellResult;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class SpellFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("SPELL_FUNCTION_ARGS_ERROR", "Spell don't have enough args.");
        }
        if (args.get(0) instanceof SpellResult v) {
            return new SpellResult(v.getSpell());
        } else if (args.get(0) instanceof ObjectResult v) {
            if (v.getObject() instanceof Spell spell) {
                return new SpellResult(spell);
            } else {
                return new ErrorResult("ERROR_IN_TYPE", "Cannot convert object to Spell.");
            }
        } else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }

    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "spell";
    }
}
