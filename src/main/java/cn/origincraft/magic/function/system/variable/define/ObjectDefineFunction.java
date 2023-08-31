package cn.origincraft.magic.function.system.variable.define;

import cn.origincraft.magic.function.HasVariableFunction;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.function.system.variable.magic.ContextMapFunction;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.*;

import java.util.List;

public class ObjectDefineFunction extends HasVariableFunction {

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size() < 2) {
            return new ErrorResult("VARIABLE_DEFINE_FUNCTION_ARGS_ERROR", "Object define don't have enough args.");
        }
        String varName="";
        if (args.get(0) instanceof StringResult name) {
            varName=name.getString();
        }else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
        if (args.get(1) instanceof StringResult v) {
            spellContext.getContextMap().putObject(varName, v.getString());
        }else if (args.get(1) instanceof DoubleResult v) {
            spellContext.getContextMap().putObject(varName, v.getDouble());
        }else if (args.get(1) instanceof BooleanResult v) {
            spellContext.getContextMap().putObject(varName, v.getBoolean());
        }else if (args.get(1) instanceof IntegerResult v) {
            spellContext.getContextMap().putObject(varName, v.getInteger());
        }else if(args.get(1) instanceof ObjectResult v) {
            spellContext.getContextMap().putObject(varName, v.getObject());
        }else if (args.get(1) instanceof LongResult v){
            spellContext.getContextMap().putObject(varName, v.getLong());
        }else if (args.get(1) instanceof FloatResult v){
            spellContext.getContextMap().putObject(varName, v.getFloat());
        }else if(args.get(1) instanceof ListResult v){
            spellContext.getContextMap().putObject(varName, v.getList());
        }else if(args.get(1) instanceof MapResult v){
            spellContext.getContextMap().putObject(varName, v.getMap());
        }else if(args.get(1) instanceof SetResult v){
            spellContext.getContextMap().putObject(varName, v.getSet());
        }else if(args.get(1) instanceof SpellResult v) {
            spellContext.getContextMap().putObject(varName, v.getSpell());
        }else if(args.get(1) instanceof NullResult){
            spellContext.getContextMap().putObject(varName, null);
        }else if(args.get(1) instanceof ContextMapResult v){
            spellContext.getContextMap().putObject(varName, v.getContextMap());
        }else if(args.get(1) instanceof ArgumentsResult v){
            spellContext.getContextMap().putObject(varName, v);
        }else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
        return new NullResult();

    }


    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "objectDefine";
    }
}
