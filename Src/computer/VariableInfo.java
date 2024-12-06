package computer;

import computer.parsernodes.Types;

public class VariableInfo {
    // NOTE: no need for variable name, stored by name in SymbolTable class
    Types type;
    boolean initialized;

    Object value;

    public VariableInfo(Types type) {
        this.type = type;
        this.initialized = false;
        this.value = null;
    }

    public VariableInfo(VariableInfo other) {
        this.type = other.type;
        this.initialized = other.initialized;
        this.value = other.value;
    }

    public Types type() {
        return type;
    }

    public void markUninitialized()
    {
        initialized = false;
        value = null;
    }
    
    public boolean isInitialized()
    {
        return initialized;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object newValue)
    {
        value = newValue;
        initialized = true;
    }
}