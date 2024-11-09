package computer;

import computer.parsernodes.Types;

import java.util.ArrayList;

public class FunctionInfo {
    Types returnType;
    ArrayList<Types> parameterTypes;

    public FunctionInfo(Types returnType, ArrayList<Types> parameterTypes) {
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
    }

    public FunctionInfo(FunctionInfo other) {
        this.returnType = other.returnType;
        // shallow copy
        this.parameterTypes = new ArrayList<Types>(other.parameterTypes);
    }

    public ArrayList<Types> parameterTypes() {
        return parameterTypes;
    }

    public Types returnType() {
        return returnType;
    }
}