package computer;

import computer.parsernodes.Types;

import java.util.ArrayList;

public class FunctionInfo {
    Types returnType;
    ArrayList<Types> parameterTypes;

    public ArrayList<Types> parameterTypes() {
        return parameterTypes;
    }

    public Types returnType() {
        return returnType;
    }
}