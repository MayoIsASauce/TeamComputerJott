package computer;

import computer.parsernodes.Types;

public class VariableInfo {
    // NOTE: no need for variable name, stored by name in SymbolTable class
    Types type;

    public Types type() {
        return type;
    }
}