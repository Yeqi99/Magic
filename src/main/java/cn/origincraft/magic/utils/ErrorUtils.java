package cn.origincraft.magic.utils;

import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class ErrorUtils {
    public static List<String> normalError(SpellContext spellContext) {
        List<String> errors = new ArrayList<>();
        ErrorResult errorResult= spellContext.getExecuteError();
        errors.add(errorResult.getErrorId()+":"+errorResult.getInfo());
        errors.addAll(errorResult.getLog());
        errors.add("Error in ");
        errors.addAll(spellContext.getExecuteErrorLocation());
        return errors;
    }
}
