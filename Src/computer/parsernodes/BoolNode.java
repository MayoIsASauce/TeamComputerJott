package computer.parsernodes;

import java.util.ArrayList;

import provided.Token;
import provided.TokenType;
import computer.exceptions.ParseException;

public class BoolNode implements ExprNode {
    boolean value;
    public BoolNode(boolean value) {
        this.value = value;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String convertToJott() {
        return value ? "True" : "False";
    }

    public static BoolNode parse(ArrayList<Token> tokens) throws ParseException {
        Token currToken = tokens.get(0);
        
        if (currToken.getTokenType() == TokenType.ID_KEYWORD) {
            if (currToken.getToken().equals("True")) {
                tokens.remove(0);
                return new BoolNode(true);
            } 
            else if (currToken.getToken().equals("False")) {
                tokens.remove(0);
                return new BoolNode(false);
            } else {
                throw new ParseException("Invalid keyword for boolNode: " + currToken.getToken() + ", must be one of (True or False)");
            }
        } else {
            throw new ParseException("Invalid TokenType for boolNode: " + currToken.getTokenType() + ", expected ID_KEYWORD");
        }
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}