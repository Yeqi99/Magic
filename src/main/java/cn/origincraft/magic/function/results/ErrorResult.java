package cn.origincraft.magic.function.results;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.expression.functions.MagicResult;

import java.util.ArrayList;
import java.util.List;

public class ErrorResult extends FunctionResult implements MagicResult {
    private final String errorId;
    private final String info;
    private List<String>  log=new ArrayList<>();

    public ErrorResult(String errorId, String info) {
        this.errorId = errorId;
        this.info = info;
    }



    public String getErrorId() {
        return errorId;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public Object getObject() {
        return errorId+":"+info;
    }

    @Override
    public String getName() {
        return "Error";
    }

    public List<String> getLog() {
        return log;
    }

    public void setLog(List<String> log) {
        this.log = log;
    }
    public void addLog(String log) {
        this.log.add(log);
    }
    public void addLog(List<String> log) {
        this.log.addAll(log);
    }
}
