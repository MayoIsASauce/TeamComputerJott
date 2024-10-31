package computer.parsernodes;

import java.util.ArrayList;

import computer.exceptions.ParseException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class TypeNode implements JottTree {
    Types type;

    public TypeNode(Types type) {
        this.type = type;
    }

    public Types type() {
        return type;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String convertToJott() {
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
                    return new TypeNode(Types.DOUBLE);
                case "Integer":
                    tokens.remove(0);
                    return new TypeNode(Types.INTEGER);
                case "String":
                    tokens.remove(0);
                    return new TypeNode(Types.STRING);
                case "Boolean":
                    tokens.remove(0);
                    return new TypeNode(Types.BOOLEAN);
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