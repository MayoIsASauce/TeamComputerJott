package computer.parsernodes;

import java.util.ArrayList;

import computer.SymbolTable;
import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class VarDeclarationNode implements JottTree {
    TypeNode type;
    IDNode id;
    public VarDeclarationNode(TypeNode type, IDNode id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public boolean validateTree() throws SemanticException
    {
        type.validateTree();
        
        // Ensure the identifier exists in the current scope
        if (!SymbolTable.instance().isVariableInCurrentScope(id.id()))
        {
            throw new SemanticException("Variable '" + id.id() +
                        "' is not in the current scope.", id.getToken());
        }

        if ( !Character.isLowerCase(id.id().charAt(0)) )
        {
            throw new SemanticException("Identifier " + id.id() + 
                        " must start with lowercase letter.", id.getToken());
        }
        
        return true;
    }

    @Override
    public String convertToJott() {
        return String.format("%s %s;", type.convertToJott(), id.convertToJott());
    }

    public static VarDeclarationNode parse(ArrayList<Token> tokens) throws ParseException {
        TypeNode nType;
        IDNode id;

        if (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
            nType = TypeNode.parse(tokens);
        } else {
            throw new ParseException("Cannot parse type from " + tokens.get(0).getTokenType() + " token.");
        }

        if (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
            id = IDNode.parse(tokens);
        } else {
            throw new ParseException("Cannot parse id from " + tokens.get(0).getTokenType() + " token.");
        }

        if (tokens.get(0).getTokenType() != TokenType.SEMICOLON) {
            throw new ParseException("Missing semicolon");
        } else {
            tokens.remove(0);
            SymbolTable.instance().addVariableToCurrentScope(id.id(), nType.type());
            return new VarDeclarationNode(nType, id);
        }
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}