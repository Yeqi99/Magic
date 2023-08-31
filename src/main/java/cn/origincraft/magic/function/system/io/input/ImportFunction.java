package cn.origincraft.magic.function.system.io.input;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.manager.MagicPackage;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.StringResult;

import java.util.List;

public class ImportFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size()<2){
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "Import function requires at least two arguments.");
        }
        FunctionResult id = args.get(0);
        FunctionResult path = args.get(1);
        if (id instanceof StringResult){
            if (path instanceof StringResult) {
                StringResult idStringResult = (StringResult) id;
                StringResult pathStringResult = (StringResult) path;
                String idString = idStringResult.getString();
                String pathString = pathStringResult.getString();
                MagicPackage magicPackage=new MagicPackage(idString);
                magicPackage.loadFiles(pathString);
                magicPackage.importPackage(spellContext);
                return new NullResult();
            }else {
                return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
            }
        }else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "import";
    }
}