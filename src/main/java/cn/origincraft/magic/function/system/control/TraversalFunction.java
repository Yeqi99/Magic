package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.ContextMap;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TraversalFunction extends NormalFunction {

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size()<2) {
            return new ErrorResult("TRAVERSAL_FUNCTION_ARGS_ERROR", "Traversal needs at least 3 arguments.");
        }
        FunctionResult arg0 = args.get(0);
        FunctionResult arg1 = args.get(1);
        if (!(arg1 instanceof SpellResult)){
            return new ErrorResult("TRAVERSAL_FUNCTION_ARGS_ERROR", "Traversal needs a spell as the second argument.");
        }
        Spell spell=((SpellResult) arg1).getSpell();
        ArgumentsResult argumentsResult=null;
        if (args.size()>2){
            List<FunctionResult> arguments=new ArrayList<>();
            arguments.containsAll(args.subList(2,args.size()));
            argumentsResult=new ArgumentsResult(arguments);
        }
        if (arg0 instanceof ListResult){
            List<Object> result=new ArrayList<>();
            List<?> container=((ListResult) arg0).getList();
            for (int i=0;i<container.size();i++){
                ContextMap contextMap=spellContext.getContextMap();
                contextMap.putVariable("index",i);
                contextMap.putVariable("value",container.get(i));
                if (argumentsResult!=null){
                    contextMap.putVariable("args",argumentsResult);
                }
                SpellContext context= spell.execute(contextMap);
                Object value=context.getContextMap().getVariable("value");
                if (value == null){
                    continue;
                }
                result.add(context.getContextMap().getVariable("value"));
                if (context.hasExecuteError()){
                    return context.getExecuteError();
                }
            }
            return new ListResult(result);
        }else if (arg0 instanceof MapResult){
            Map<Object,Object> result=new ConcurrentHashMap<>();
            Map<?,?> container=((MapResult) arg0).getMap();
            for (Map.Entry<?,?> entry:container.entrySet()){
                ContextMap contextMap=spellContext.getContextMap();
                contextMap.putVariable("key",entry.getKey());
                contextMap.putVariable("value",entry.getValue());
                if (argumentsResult!=null){
                    contextMap.putVariable("args",argumentsResult);
                }
                SpellContext context= spell.execute(contextMap);
                Object value=context.getContextMap().getVariable("value");
                if (value == null){
                    continue;
                }
                result.put(context.getContextMap().getVariable("key"),context.getContextMap().getVariable("value"));
                if (context.hasExecuteError()){
                    return context.getExecuteError();
                }
            }
            return new MapResult(result);
        }else if (arg0 instanceof SetResult){
            Set<?> inContainer=((SetResult) arg0).getSet();
            Set<Object> result=new HashSet<>();
            List<?> container=new ArrayList<>(inContainer);
            for (int i=0;i<container.size();i++){
                ContextMap contextMap=spellContext.getContextMap();
                contextMap.putVariable("index",i);
                contextMap.putVariable("value",container.get(i));
                if (argumentsResult!=null){
                    contextMap.putVariable("args",argumentsResult);
                }
                SpellContext context= spell.execute(contextMap);
                Object value=context.getContextMap().getVariable("value");
                if (value == null){
                    continue;
                }
                result.add(context.getContextMap().getVariable("value"));
                if (context.hasExecuteError()){
                    return context.getExecuteError();
                }
            }
            return new SetResult(result);
        }
        return new NullResult();
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "traversal";
    }
}
