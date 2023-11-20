package cn.origincraft.magic.function;

import cn.origincraft.magic.expression.functions.FastFunction;
import cn.origincraft.magic.expression.functions.FunctionResult;

import java.util.ArrayList;
import java.util.List;

public class ArgsSetting {

    private List<String> argsTypes=new ArrayList<>();
    private List<String> info=new ArrayList<>();
    private String resultType="Object";
    private String id;

    public ArgsSetting(List<String> argsTypes, List<String> info, String resultType) {
        this.argsTypes = argsTypes;
        this.info = info;
        this.resultType = resultType;
    }
    public ArgsSetting(String id,List<String> argsTypes, List<String> info, String resultType) {
        this.id=id;
        this.argsTypes = argsTypes;
        this.info = info;
        this.resultType = resultType;
    }
    public ArgsSetting(String id){
        this.id=id;
    }
    public ArgsSetting(){
    }

    public boolean checkArgs(List<FunctionResult> args) {
        return checkArgsAmount(args) && checkArgsType(args);
    }

    public boolean checkArgsAmount(List<FunctionResult> args) {
        if (argsTypes.contains("...")){
            return true;
        }
        return args.size() == argsTypes.size();
    }
    public boolean checkArgsType(List<FunctionResult> args) {
        for (int i = 0; i < argsTypes.size(); i++) {
            String type = argsTypes.get(i);
            if (type.equalsIgnoreCase("...")){
                return true;
            }
            if (type.equalsIgnoreCase("Object") || type.equalsIgnoreCase(".")){
                continue;
            }
            if (!args.get(i).getName().equals(type)) return false;
        }
        return true;
    }


    public List<String> getArgsTypes() {
        return argsTypes;
    }

    public ArgsSetting setArgsTypes(List<String> argsTypes) {
        this.argsTypes = argsTypes;
        return this;
    }

    public List<String> getInfo() {
        return info;
    }

    public ArgsSetting setInfo(List<String> info) {
        this.info = info;
        return this;
    }

    public String getResultType() {
        return resultType;
    }

    public ArgsSetting setResultType(String resultType) {
        this.resultType = resultType;
        return this;
    }

    public String getId() {
        return id;
    }

    public ArgsSetting setId(String id) {
        this.id = id;
        return this;
    }
    public ArgsSetting addInfo(String line){
        info.add(line);
        return this;
    }
    public ArgsSetting addArgType(String type){
        argsTypes.add(type);
        return this;
    }
    public ArgsSetting addArgType(FastFunction fastFunction){
        argsTypes.add(fastFunction.getName());
        return this;
    }
}
