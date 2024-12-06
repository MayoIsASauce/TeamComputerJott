package computer.parsernodes;

import provided.Token;
import provided.TokenType;
import computer.SymbolTable;
import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;
import computer.exceptions.RuntimeException;

import java.util.ArrayList;

public class IDNode implements OperandNode {
    Token token;

    public IDNode(Token token) {
        this.token = token;
    }

    public String id() {
        return token.getToken();
    }

    @Override
    public Token getToken() { return token; }

    @Override
    public boolean validateTree() throws SemanticException
    {
        // Ensure the identifier exists in the current scope
        if (!SymbolTable.instance().isVariableInCurrentScope(id()))
        {
            throw new SemanticException("Variable '" + id() +
                        "' is not in the current scope.", token);
        }

        if ( !Character.isLowerCase(id().charAt(0)) )
        {
            throw new SemanticException("Identifier " + id() + 
                        " must start with lowercase letter.", token);
        }

        return true;
    }

    @Override
    public Types getDataType()
    {
        // Retrieve the variable's type from the current scope
        if (!SymbolTable.instance().isVariableInCurrentScope(id()))
        {
            return null;
        }

        return SymbolTable.instance().currentScopeVar(id()).type();
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
    public void execute() throws RuntimeException {
        /// dont call this function, exprs should return something
        assert false;
    }

    @Override
    public Object executeAndReturnData() throws RuntimeException 
    {
        // Ensure that variable is initialized
        if (!SymbolTable.instance().isVariableInitialized(id()))
        {
            throw new RuntimeException("Attempt to use uninitialized variable '"
                        + id() + "' in expression.", token);
        }

        return SymbolTable.instance().currentScopeVar(id()).value;
    }
}