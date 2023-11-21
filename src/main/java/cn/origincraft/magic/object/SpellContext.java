package cn.origincraft.magic.object;


import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.results.ErrorResult;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpellContext {
    private ContextMap contextMap;

    // 执行字典
    private ConcurrentHashMap<String, Object> executeResultMap = new ConcurrentHashMap<>();


    public SpellContext() {
        putExecuteNext(0);
    }


    /*
    语境相关封装方法
     */

    // 记录上下文中已执行的语句条数相关方法
    public int getExecuteCount() {
        if (!hasExecuteCount()) {
            return 0;
        }
        return (int) getExecuteResultMap().get("execute.count");
    }

    public void putExecuteCount(int num) {
        putExecuteResult("execute.count", num);
    }

    public boolean hasExecuteCount() {
        return getExecuteResultMap().containsKey("execute.count");
    }

    public void addExecuteCount(int addValue) {
        putExecuteCount(getExecuteCount() + addValue);
    }

    // 记录执行时的循环序号
    public int getExecuteIndex() {
        if (!hasExecuteIndex()) {
            return 0;
        }
        return (int) getExecuteResultMap().get("execute.index");
    }

    public void putExecuteIndex(int num) {
        putExecuteResult("execute.index", num);
    }

    public boolean hasExecuteIndex() {
        return getExecuteResultMap().containsKey("execute.index");
    }

    // 下一条执行的语句序号
    public int getExecuteNext() {
        if (!hasExecuteNext()) {
            return 0;
        }
        return (int) getExecuteResultMap().get("execute.next");
    }

    public void putExecuteNext(int num) {
        putExecuteResult("execute.next", num);
    }

    public boolean hasExecuteNext() {
        return getExecuteResultMap().containsKey("execute.next");
    }

    // 上一条执行的语句序号
    public int getExecutePrevious() {
        if (!hasExecutePrevious()) {
            return 0;
        }
        return (int) getExecuteResultMap().get("execute.previous");
    }

    public void putExecutePrevious(int num) {
        putExecuteResult("execute.previous", num);
    }

    public boolean hasExecutePrevious() {
        return getExecuteResultMap().containsKey("execute.previous");
    }

    // 中断语句队列
    public void putExecuteBreak(boolean bool) {
        putExecuteResult("execute.break", bool);
    }

    public boolean getExecuteBreak() {
        if (!hasExecuteBreak()) {
            return false;
        }
        return (boolean) getExecuteResultMap().get("execute.break");
    }

    public boolean hasExecuteBreak() {
        return getExecuteResultMap().containsKey("execute.break");
    }

    // 跳过下一条语句
    public void putExecuteContinue(boolean bool) {
        putExecuteResult("execute.continue", bool);
    }

    public void removeExecuteContinue() {
        if (hasExecuteContinue()) {
            getExecuteResultMap().remove("execute.continue");
        }
    }

    public boolean getExecuteContinue() {
        if (!hasExecuteContinue()) {
            return false;
        }
        return (boolean) getExecuteResultMap().get("execute.continue");
    }

    public boolean hasExecuteContinue() {
        return getExecuteResultMap().containsKey("execute.continue");
    }

    // 跳过下面的num条语句
    public void putExecutePass(int num) {
        putExecuteResult("execute.pass", num);
    }

    public void removeExecutePass() {
        if (hasExecutePass()) {
            getExecuteResultMap().remove("execute.pass");
        }
    }

    public int getExecutePass() {
        if (!hasExecutePass()) {
            return 0;
        }
        return (int) getExecuteResultMap().get("execute.pass");
    }

    public boolean hasExecutePass() {
        return getExecuteResultMap().containsKey("execute.pass");
    }

    public void putExecuteParameter(String parameter) {
        putExecuteResult("execute.parameter", parameter);
    }

    public String getExecuteParameter() {
        if (!hasExecuteParameter()) {
            return null;
        }
        return (String) getExecuteResultMap().get("execute.parameter");
    }

    public boolean hasExecuteParameter() {
        return getExecuteResultMap().containsKey("execute.parameter");
    }

    public void putMagicManager(MagicManager magicManager) {
        getExecuteResultMap().put("magic.manager", magicManager);
    }

    public MagicManager getMagicManager() {
        if (!hasMagicManager()) {
            return null;
        }
        return (MagicManager) getExecuteResultMap().get("magic.manager");
    }

    public boolean hasMagicManager() {
        return getExecuteResultMap().containsKey("magic.manager");
    }

    public void putExecuteIndexAllow(int index, boolean bool) {
        getExecuteResultMap().put("execute.+" + index + ".allow", bool);
    }

    public boolean hasExecuteIndexAllow(int index) {
        return getExecuteResultMap().containsKey("execute.+" + index + ".allow");
    }

    public boolean getExecuteIndexAllow(int index) {
        if (!hasExecuteIndexAllow(index)) {
            return true;
        }
        return (boolean) getExecuteResultMap().get("execute.+" + index + ".allow");
    }

    public void removeExecuteIndexAllow(int index) {
        if (hasExecuteIndexAllow(index)) {
            getExecuteResultMap().remove("execute.+" + index + ".allow");
        }
    }

    public void putExecuteError(FunctionResult error) {
        getExecuteResultMap().put("execute.error", error);
    }

    public void removeExecuteError() {
        if (hasExecuteError()) {
            getExecuteResultMap().remove("execute.error");
        }
    }

    public boolean hasExecuteError() {
        return getExecuteResultMap().containsKey("execute.error");
    }

    public void putExecuteErrorLocation(List<String> info) {
        getExecuteResultMap().put("execute.error.location", info);
    }

    public void removeExecuteErrorLocation() {
        if (hasExecuteErrorLocation()) {
            getExecuteResultMap().remove("execute.error.location");
        }
    }

    public boolean hasExecuteErrorLocation() {
        return getExecuteResultMap().containsKey("execute.error.location");
    }

    public List<String> getExecuteErrorLocation() {
        if (!hasExecuteErrorLocation()) {
            return null;
        }
        return (List<String>) getExecuteResultMap().get("execute.error.location");
    }

    public ErrorResult getExecuteError() {
        if (!hasExecuteError()) {
            return null;
        }
        return (ErrorResult) getExecuteResultMap().get("execute.error");
    }

    public Map<String, Object> getExecuteResultMap() {
        return executeResultMap;
    }


    public void setExecuteResultMap(ConcurrentHashMap<String, Object> executeResultMap) {
        this.executeResultMap = executeResultMap;
    }

    public void putObject(String key, Object value) {
        contextMap.putObject(key, value);
    }

    public void putVariable(String key, Object value) {
        contextMap.putVariable(key, value);
    }

    public void putExecuteResult(String key, Object value) {
        getExecuteResultMap().put(key, value);
    }

    public void putSpellReturn(FunctionResult functionResult) {
        getExecuteResultMap().put("spellReturn", functionResult);
    }

    public FunctionResult getSpellReturn() {
        return (FunctionResult) getExecuteResultMap().get("spellReturn");
    }

    public boolean hasSpellReturn() {
        return getExecuteResultMap().containsKey("spellReturn");
    }


    public ContextMap getContextMap() {
        return contextMap;
    }

    public void setContextMap(ContextMap contextMap) {
        this.contextMap = contextMap;
    }

    public void importContextMap(ContextMap contextMap){
        for (String variableName : this.contextMap.getVariableNames()) {
            this.contextMap.putVariable(variableName,contextMap.getVariable(variableName));
        }
        for (String variableName : this.contextMap.getObjectNames()) {
            this.contextMap.putObject(variableName,contextMap.getObject(variableName));
        }
    }
    public void addPrintLog(String message){
        String s=getPrintLog();
        if (s==null){
            if (message==null){
                return;
            }
            getExecuteResultMap().put("printLog",message);
        }else {
            getExecuteResultMap().put("printLog",s+"\n"+message);
        }

    }
    public String getPrintLog(){
        Object object=getExecuteResultMap().get("printLog");
        if (object==null){
            return null;
        }
        return (String) object;
    }
}
