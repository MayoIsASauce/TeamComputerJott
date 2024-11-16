package computer.parsernodes;

import java.util.ArrayList;

import computer.exceptions.ParseException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class TypeNode implements JottTree {
    Types type;
    Token tokenRepresentation;

    public TypeNode(Types type, Token token) {
        this.type = type;
        this.tokenRepresentation = token;
    }

    public Types type() {
        return type;
    }

    Token getToken() { return tokenRepresentation; }

    @Override
    public boolean validateTree() {
        return true;
    }

    @Override
    public String convertToJott() {
        // NOTE: switches in TypeNode do not handle "Void" because that is only allowed
        // in a function return type, so that is parsed and handled in FuncReturnNode
        switch (type) {
            case DOUBLE:
                return "Double";
            case BOOLEAN:
                return "Boolean";
            case STRING:
                return "String";
            case INTEGER:
                return "Integer";
            default:
                return null;
        }
    }

    public static TypeNode parse(ArrayList<Token> tokens) throws ParseException {
        Token curr_token = tokens.get(0);
        if (curr_token.getTokenType() == TokenType.ID_KEYWORD) {
            switch (curr_token.getToken()) {
                case "Double":
                    tokens.remove(0);
                    return new TypeNode(Types.DOUBLE, curr_token);
                case "Integer":
                    tokens.remove(0);
                    return new TypeNode(Types.INTEGER, curr_token);
                case "String":
                    tokens.remove(0);
                    return new TypeNode(Types.STRING, curr_token);
                case "Boolean":
                    tokens.remove(0);
                    return new TypeNode(Types.BOOLEAN, curr_token);
                default:
                    throw new ParseException("Parser Exception\nTypeNode received invalid type \""+curr_token.getToken()+"\", expected one of type: (Double, Integer, String, Boolean)");
            }
        } 
        throw new ParseException("Parser Exception\nTypeNode receieved invalid tokenType: "+curr_token.getTokenType()+", only accepts ID_KEYWORD");

    }
    

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}