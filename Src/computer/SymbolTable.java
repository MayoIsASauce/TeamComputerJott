package computer;

import computer.parsernodes.FuncBodyNode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Stack;
import computer.parsernodes.Types;

public class SymbolTable {

    static SymbolTable instance = null;
    static HashSet<String> reservedFunctions = new HashSet<>(Set.of(
        "print",
        "concat",
        "length"
        ));

    // symbol table gets built at parse, then is readonly during execution
    boolean completed;
    HashMap<String, FunctionInfo> functionInfosByName;
    HashMap<String, HashMap<String, VariableInfo>> variableInfoByNameByFuncName;
    Stack<String> programStack;

    public static SymbolTable instance() {
        assert instance != null;
        return instance;
    }

    public SymbolTable() {
        assert instance == null; // only one instance allowed :)
        instance = this;
        completed = false;
        programStack = new Stack<String>();
        functionInfosByName = new HashMap<String, FunctionInfo>();
        variableInfoByNameByFuncName = new HashMap<String, HashMap<String, VariableInfo>>();
    }

    public String currentScope() {
        return programStack.peek();
    }

    /// Returns a copy of the current scope's info
    public FunctionInfo functionInfo(String functionName) {
        assert functionInfosByName.containsKey(functionName);
        return new FunctionInfo(functionInfosByName.get(functionName));
    }

    public boolean containsFunction(String functionName)
    {
        return functionInfosByName.containsKey(functionName);
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
    public boolean isVariableInCurrentScope(String variableName) {
        HashMap<String, VariableInfo> variableInfoByName = variableInfoByNameByFuncName.get(currentScope());
        return variableInfoByName.containsKey(variableName);
    }

    public void setVariableValue(String variableName, Object value)
    {
        assert variableInfoByNameByFuncName.get(currentScope()).containsKey(variableName);
        variableInfoByNameByFuncName.get(currentScope()).get(variableName).setValue(value);
    }

    public boolean isVariableInitialized(String variableName)
    {
        assert variableInfoByNameByFuncName.get(currentScope()).containsKey(variableName);
        return variableInfoByNameByFuncName.get(currentScope()).get(variableName).isInitialized();
    }

    /// For use after symbol table has been built
    public void enterScope(String functionName) {
        assert completed;
        assert variableInfoByNameByFuncName.containsKey(functionName);
        assert functionInfosByName.containsKey(functionName);
        programStack.push(functionName);
    }

    /// For use after the symbol table has been built
    public void exitScope() {
        assert !programStack.empty();
        assert completed;

        // Clear all vars
        HashMap<String, VariableInfo> variableInfoByName = variableInfoByNameByFuncName.get(currentScope());
        for (String varName : variableInfoByName.keySet())
        {
            variableInfoByName.get(varName).markUninitialized();
        }

        programStack.pop();
    }

    public void beginBuildingNewScope(String functionName, Types returnType, ArrayList<Types> parameterTypes,
                    ArrayList<String> parameterNames) {
        assert !completed;
        assert programStack.empty();
        assert !variableInfoByNameByFuncName.containsKey(functionName);
        assert !functionInfosByName.containsKey(functionName);
        HashMap<String, VariableInfo> scope = new HashMap<String, VariableInfo>();
        variableInfoByNameByFuncName.put(functionName, scope);
        functionInfosByName.put(functionName, new FunctionInfo(returnType, parameterTypes, parameterNames));
        programStack.push(functionName);
    }

    /// Only valid to call while building symbol table, before execution
    public void finishBuildingCurrentScope(FuncBodyNode linkBodyNode) {
        functionInfosByName.get(currentScope()).setFuncBody(linkBodyNode);
        assert !completed;
        assert programStack.size() == 1;
        programStack.pop();
    }

    /// Only valid to call while building symbol table, before execution
    public void addVariableToCurrentScope(String variableName, Types type) {
        assert !completed;
        HashMap<String, VariableInfo> variableInfoByName = variableInfoByNameByFuncName.get(currentScope());
        assert !variableInfoByName.containsKey(variableName);
        variableInfoByName.put(variableName, new VariableInfo(type));
    }

    /// Before calling this, it is only valid to call beginBuildingNewScope,
    /// finishBuildingCurrentScope, and addVariableToCurrentScope.
    /// After calling this, it is only valid to call enterScope and exitScope.
    /// Unmentioned functions like getters (currentScope, functionInfo) are
    /// always valid to call regardless of finalization.
    public void finalizeTable() {
        assert !completed;
        assert programStack.empty();
        completed = true;
    }

    public boolean isReservedFunction(String funcName)
    {
        return reservedFunctions.contains(funcName);
    }
}