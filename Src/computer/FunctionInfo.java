package computer;

import computer.parsernodes.FuncBodyNode;
import computer.parsernodes.Types;
import java.util.ArrayList;

public class FunctionInfo {
    Types returnType;
    ArrayList<Types> parameterTypes;
    ArrayList<String> parameterNames;
    FuncBodyNode linkToFuncBody;

    public FunctionInfo(Types returnType, ArrayList<Types> parameterTypes, ArrayList<String> parameterNames) {
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.linkToFuncBody = null;
        this.parameterNames = parameterNames;
    }



    public FunctionInfo(FunctionInfo other) {
        this.returnType = other.returnType;
        // shallow copy
        this.parameterTypes = new ArrayList<Types>(other.parameterTypes);
        this.parameterNames = new ArrayList<String>(other.parameterNames);
        this.linkToFuncBody = other.linkToFuncBody;
    }

    public ArrayList<Types> parameterTypes() {
        return parameterTypes;
    }

    public ArrayList<String> parameterNames() {
        return parameterNames;
    }

    public Types returnType() {
        return returnType;
    }

    public FuncBodyNode linkToFuncBody() {
        return linkToFuncBody;
    }

    public void setFuncBody(FuncBodyNode funcBody) {
        this.linkToFuncBody = funcBody;
    }

}