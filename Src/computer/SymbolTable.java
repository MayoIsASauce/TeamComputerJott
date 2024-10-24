package computer;

import java.util.HashMap;
import computer.FunctionInfo;
import computer.VariableInfo;

public class SymbolTable {

    static SymbolTable instance = null;

    String currentScope;
    HashMap<String, FunctionInfo> functionInfosByName;
    HashMap<String, HashMap<String, VariableInfo>> variableInfoByFuncNameByVarName;

    public static SymbolTable instance() {
        assert instance != null;
        return instance;
    }

    public String currentScope() {
        return currentScope;
    }

    public SymbolTable() {
        instance = this;
        currentScope = null;
        functionInfosByName = new HashMap<String, FunctionInfo>();
        variableInfoByFuncNameByVarName = new HashMap<String, HashMap<String, VariableInfo>>();
    }

    // TODO
    public void enterScope(String functionName) {}

    // TODO
    public void addVariableToCurrentScope(String variableName) {}

    // TODO
    public void exitScope() {}
}