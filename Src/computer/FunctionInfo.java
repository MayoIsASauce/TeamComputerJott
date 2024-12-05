package computer;

import computer.parsernodes.FuncBodyNode;
import computer.parsernodes.Types;
import java.util.ArrayList;

public class FunctionInfo {
    Types returnType;
    ArrayList<Types> parameterTypes;
    FuncBodyNode linkToFuncBody;

    public FunctionInfo(Types returnType, ArrayList<Types> parameterTypes) {
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.linkToFuncBody = null;
    }

    public FunctionInfo(Types returnType, ArrayList<Types> parameterTypes, FuncBodyNode linkToFuncBody) {
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.linkToFuncBody = linkToFuncBody;
    }



    public FunctionInfo(FunctionInfo other) {
        this.returnType = other.returnType;
        // shallow copy
        this.parameterTypes = new ArrayList<Types>(other.parameterTypes);
        this.linkToFuncBody = other.linkToFuncBody;
    }

    public ArrayList<Types> parameterTypes() {
        return parameterTypes;
    }

    public Types returnType() {
        return returnType;
    }

    public FuncBodyNode linkToFuncBody() {
        return linkToFuncBody;
    }

    public void setFuncBody(FuncBodyNode funcBody) {
        linkToFuncBody = funcBody;
    }

}