package cn.origincraft.magic.function;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.expression.functions.MagicResult;

import java.util.List;

public class ArgsSetting {
    private int argsAmount;
    private List<String> argsTypes;
    private List<String> info;
    private String resultType;
    private String id;

    public ArgsSetting(int argsAmount, List<String> argsTypes, List<String> info, String resultType) {
        this.argsAmount = argsAmount;
        this.argsTypes = argsTypes;
        this.info = info;
        this.resultType = resultType;
    }

    public boolean checkArgs(List<FunctionResult> args) {
        return checkArgsAmount(args) && checkArgsType(args);
    }

    public boolean checkArgsAmount(List<FunctionResult> args) {
        return args.size() == argsAmount;
    }

    public boolean checkArgsType(List<FunctionResult> args) {
        for (int i = 0; i < args.size(); i++) {
            String type = argsTypes.get(i);
            MagicResult magicResult = args.get(i);
            if (!magicResult.getName().equals(type)) return false;
        }
        return true;
    }

    public int getArgsAmount() {
        return argsAmount;
    }

    public void setArgsAmount(int argsAmount) {
        this.argsAmount = argsAmount;
    }

    public List<String> getArgsTypes() {
        return argsTypes;
    }

    public void setArgsTypes(List<String> argsTypes) {
        this.argsTypes = argsTypes;
    }

    public List<String> getInfo() {
        return info;
    }

    public void setInfo(List<String> info) {
        this.info = info;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
