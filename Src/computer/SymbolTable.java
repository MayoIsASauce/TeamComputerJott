package computer;

import java.util.HashMap;
import java.util.ArrayList;
import computer.parsernodes.Types;

public class SymbolTable {

    static SymbolTable instance = null;

    // symbol table gets built at parse, then is readonly during execution
    boolean completed;
    String currentScope;
    HashMap<String, FunctionInfo> functionInfosByName;
    HashMap<String, HashMap<String, VariableInfo>> variableInfoByNameByFuncName;

    public static SymbolTable instance() {
        assert instance != null;
        return instance;
    }

    public SymbolTable() {
        assert instance == null; // only one instance allowed :)
        instance = this;
        completed = false;
        currentScope = null;
        functionInfosByName = new HashMap<String, FunctionInfo>();
        variableInfoByNameByFuncName = new HashMap<String, HashMap<String, VariableInfo>>();
    }

    public String currentScope() {
        return currentScope;
    }

    /// Returns a copy of the current scope's info
    public FunctionInfo functionInfo(String functionName) {
        assert functionInfosByName.containsKey(functionName);
        return new FunctionInfo(functionInfosByName.get(functionName));
    }

    /// Returns a copy of variable info about a variable in a given scope.
    public VariableInfo variableInfoForFunction(String functionName, String variableName) {
        assert variableInfoByNameByFuncName.containsKey(functionName);
        HashMap<String, VariableInfo> variableInfoByName = variableInfoByNameByFuncName.get(functionName);
        assert variableInfoByName.containsKey(variableName);
        return new VariableInfo(variableInfoByName.get(variableName));
    }

    // Shorthand
    public VariableInfo currentScopeVar(String variableName) {
        return variableInfoForFunction(currentScope(), variableName);
    }
    public FunctionInfo currentScopeInfo() {
        return functionInfo(currentScope());
    }

    /// For use after symbol table has been built
    public void enterScope(String functionName) {
        // no nested scopes in jott. exitScope() required, although technically
        // we could just allow enterScope to be called and it just overwrite the
        // previous scope
        assert currentScope == null; 
        assert completed;
        assert variableInfoByNameByFuncName.containsKey(functionName);
        assert functionInfosByName.containsKey(functionName);
        currentScope = functionName;
    }

    /// For use after the symbol table has been built
    public void exitScope() {
        assert currentScope != null;
        assert completed;
        currentScope = null;
    }

    public void beginBuildingNewScope(String functionName, Types returnType, ArrayList<Types> parameterTypes) {
        assert !completed;
        assert currentScope == null;
        assert !variableInfoByNameByFuncName.containsKey(functionName);
        assert !functionInfosByName.containsKey(functionName);
        HashMap<String, VariableInfo> scope = new HashMap<String, VariableInfo>();
        variableInfoByNameByFuncName.put(functionName, scope);
        functionInfosByName.put(functionName, new FunctionInfo(returnType, parameterTypes));
        currentScope = functionName;
    }

    /// Only valid to call while building symbol table, before execution
    public void finishBuildingCurrentScope() {
        assert !completed;
        assert currentScope != null;
        currentScope = null;
    }

    /// Only valid to call while building symbol table, before execution
    public void addVariableToCurrentScope(String variableName, Types type) {
        assert !completed;
        HashMap<String, VariableInfo> variableInfoByName = variableInfoByNameByFuncName.get(currentScope);
        assert !variableInfoByName.containsKey(variableName);
        variableInfoByName.put(variableName, new VariableInfo(type));
    }

    /// Before calling this, it is only valid to call beginBuildingNewScope,
    /// finishBuildingCurrentScope, and addVariableToCurrentScope.
    /// After calling this, it is only valid to call enterScope and exitScope.
    /// Unmentioned functions like getters (currentScope, functionInfo) are
    /// always valid to call regardless of finalization.
    public void finalize() {
        assert !completed;
        assert currentScope == null;
        completed = true;
    }
}