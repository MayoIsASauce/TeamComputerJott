package computer.parsernodes;

import java.util.ArrayList;

import provided.Token;
import provided.TokenType;
import computer.exceptions.ParseException;
import computer.SymbolTable;

public class IDNode implements OperandNode {
    Token id;

    public IDNode(Token id) {
        this.id = id;
    }

    public Token getToken() { return id; }

    public String id() {
        return id.getToken();
    }

    @Override
    public boolean validateTree() {
        // Ensure the identifier exists in the symbol table
        if (!SymbolTable.instance().isVariableDeclared(id)) {
            System.err.println("Semantic Error: Variable '" + id + "' is not declared.");
            return false;
        }
        return true;
    }

    @Override
    public Types getDataType() {
        // Retrieve the variable's type from the symbol table
        if (!SymbolTable.instance().isVariableDeclared(id)) {
            System.err.println("Semantic Error: Variable '" + id + "' is not declared.");
            return null;
        }
        return SymbolTable.instance().getVariableType(id);
    }

    @Override
    public String convertToJott() {
        return id();
    }

    public static IDNode parse(ArrayList<Token> tokens) throws ParseException {
        Token token = tokens.get(0);

        if (token.getTokenType() != TokenType.ID_KEYWORD)
            throw new ParseException("Attempt to parse non-ID token as ID: " + token.getToken());

        tokens.remove(0);

        return new IDNode(token);
    }

    @Override
    public void execute() {
        // To be implemented in Phase 4
    }
}