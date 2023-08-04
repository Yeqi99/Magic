package cn.origincraft.magic.object;


import cn.origincraft.magic.MagicManager;

import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SpellContext {
    // 目标字典
    private Map<String, Object> objectMap = new HashMap<>();
    // 执行字典
    private Map<String, Object> executeResultMap = new HashMap<>();
    // 变量字典
    private Map<String, Object> variableMap = new HashMap<>();

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
        return getObjectMap().containsKey("execute.count");
    }

    public void addExecuteCount(int addValue) {
        putExecuteCount(getExecuteCount()+addValue);
    }

    // 记录执行时的循环序号
    public int getExecuteIndex() {
        if (!hasExecuteIndex()) {
            return 0;
        }
        return (int) getObjectMap().get("execute.index");
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
        putObject("execute.break", bool);
    }
    public boolean getExecuteBreak(){
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
        if (hasExecuteContinue()){
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
        if (hasExecutePass()){
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

    public void putMagicManager(MagicManager magicManager){
        getExecuteResultMap().put("magic.manager",magicManager);
    }
    public MagicManager getMagicManager(){
        if (!hasMagicManager()){
            return null;
        }
        return (MagicManager) getExecuteResultMap().get("magic.manager");
    }
    public boolean hasMagicManager(){
        return getExecuteResultMap().containsKey("magic.manager");
    }

    public void putExecuteIndexAllow(int index,boolean bool){
        getExecuteResultMap().put("execute.+"+index+".allow",bool);
    }
    public boolean hasExecuteIndexAllow(int index){
        return getExecuteResultMap().containsKey("execute.+"+index+".allow");
    }
    public boolean getExecuteIndexAllow(int index){
        if (!hasExecuteIndexAllow(index)){
            return true;
        }
        return (boolean) getExecuteResultMap().get("execute.+"+index+".allow");
    }
    public void removeExecuteIndexAllow(int index){
        if (hasExecuteIndexAllow(index)){
            getExecuteResultMap().remove("execute.+"+index+".allow");
        }
    }



    public Map<String, Object> getExecuteResultMap() {
        return executeResultMap;
    }

    public void setExecuteResultMap(Map<String, Object> executeResultMap) {
        this.executeResultMap = executeResultMap;
    }

    public Map<String, Object> getVariableMap() {
        return variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }

    public Map<String, Object> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map<String, Object> objectMap) {
        this.objectMap = objectMap;
    }
    public void putObject(String key,Object value){
        getObjectMap().put(key,value);
    }
    public void putVariable(String key,Object value){
        getVariableMap().put(key,value);
    }
    public void putExecuteResult(String key,Object value){
        getExecuteResultMap().put(key,value);
    }
}
