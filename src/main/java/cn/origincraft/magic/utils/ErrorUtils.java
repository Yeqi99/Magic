package cn.origincraft.magic.utils;

import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class ErrorUtils {
    public static List<String> normalError(SpellContext spellContext) {
        List<String> errors = new ArrayList<>();
        ErrorResult errorResult = spellContext.getExecuteError();
        errors.add(errorResult.getErrorId() + ":" + errorResult.getInfo());
        errors.addAll(errorResult.getLog());
        errors.add("Error in ");
        List<String> location = spellContext.getExecuteErrorLocation();
        if (location == null || location.isEmpty()) {
            errors.add("Unknown location");
        } else {
            errors.addAll(location);
        }
        return errors;
    }

    public static String stringError(SpellContext spellContext) {
        List<String> errors = normalError(spellContext);
        StringBuilder string= new StringBuilder();
        for (String error : errors) {
            string.append(error).append("\n");
        }
        return string.substring(0,string.length()-1);
    }
}
