package computer;

import computer.parsernodes.Types;

public class VariableInfo {
    // NOTE: no need for variable name, stored by name in SymbolTable class
    Types type;
    boolean initialized;

    public VariableInfo(Types type) {
        this.type = type;
        this.initialized = false;
    }

    public VariableInfo(VariableInfo other) {
        this.type = other.type;
        this.initialized = other.initialized;
    }

    public Types type() {
        return type;
    }

    public void markInitialized()
    {
        initialized = true;
    }
    
    public boolean isInitialized()
    {
        return initialized;
    }
}