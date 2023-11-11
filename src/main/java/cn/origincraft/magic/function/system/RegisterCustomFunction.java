package cn.origincraft.magic.function.system;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.NormalContext;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class RegisterCustomFunction extends ArgsFunction {
//    @Override
//    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
//        if (args.size() < 2) {
//            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "RegisterCustomMethod function requires at least two arguments");
//        }
//        FunctionResult spell = args.get(0);
//        FunctionResult name = args.get(1);
//        FunctionResult type = new StringResult("CUSTOM");
//        if (args.size() >= 3) {
//            type = args.get(2);
//        }
//        if (!(spell instanceof SpellResult)) {
//            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "First argument of RegisterCustomMethod function must be a spell.");
//        }
//        if (!(name instanceof StringResult)) {
//            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Second argument of RegisterCustomMethod function must be a string.");
//        }
//        if (!(type instanceof StringResult)) {
//            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Third argument of RegisterCustomMethod function must be a string.");
//        }
//        FunctionResult finalType = type;
//        spellContext.getMagicManager().registerFunction(new ArgsFunction() {
//            @Override
//            public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
//                NormalContext context = new NormalContext();
//                context.putVariable("args", new ArgumentsResult(args));
//                SpellContext temp = ((SpellResult) spell).getSpell().execute(context);
//                if (!temp.hasSpellReturn()) {
//                    return new NullResult();
//                }
//                return temp.getSpellReturn();
//            }
//
//            @Override
//            public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
//                return null;
//            }
//
//            @Override
//            public List<ArgsSetting> getArgsSetting() {
//                return null;
//            }
//
//            @Override
//            public String getType() {
//                return ((StringResult) finalType).getString();
//            }
//
//            @Override
//            public String getName() {
//                return ((StringResult) name).getString();
//            }
//        });
//        return new NullResult();
//    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        return null;
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                    .addArgType("Spell String String")
                        .addInfo("spell name type")
                        .addInfo("Register a custom method.")
                        .addInfo("spell: The spell to be registered.")
                        .addInfo("name: The name of the method.")
                        .addInfo("type: The type of the method.")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "registerCustom";
    }
}
