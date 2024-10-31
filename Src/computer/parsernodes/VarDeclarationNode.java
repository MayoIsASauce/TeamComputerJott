package computer.parsernodes;

import java.util.ArrayList;

import computer.SymbolTable;
import computer.exceptions.ParseException;
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
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
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