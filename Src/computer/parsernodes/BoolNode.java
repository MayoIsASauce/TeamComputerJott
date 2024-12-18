package computer.parsernodes;

import java.util.ArrayList;

import provided.Token;
import provided.TokenType;
import computer.exceptions.ParseException;
import computer.exceptions.RuntimeException;

public class BoolNode implements ExprNode {
    boolean value;
    Token tokenRepresentation;

    public BoolNode(boolean value, Token token) {
        this.tokenRepresentation = token;
        this.value = value;
    }

    @Override
    public Token getToken() { return tokenRepresentation; }

    @Override
    public boolean validateTree() { return true; }

    @Override
    public Types getDataType() { return Types.BOOLEAN; }

    @Override
    public void execute() throws RuntimeException {
        /// dont call this function, exprs should return something
        assert false;
    }

    @Override
    public Object executeAndReturnData() throws RuntimeException { return value; }

    @Override
    public String convertToJott() {
        return value ? "True" : "False";
    }

    public static BoolNode parse(ArrayList<Token> tokens) throws ParseException {
        Token currToken = tokens.get(0);
        
        if (currToken.getTokenType() == TokenType.ID_KEYWORD) {
            if (currToken.getToken().equals("True")) {
                tokens.remove(0);
                return new BoolNode(true, currToken);
            } 
            else if (currToken.getToken().equals("False")) {
                tokens.remove(0);
                return new BoolNode(false, currToken);
            } else {
                throw new ParseException("Invalid keyword for boolNode: " + currToken.getToken() + ", must be one of (True or False)");
            }
        } else {
            throw new ParseException("Invalid TokenType for boolNode: " + currToken.getTokenType() + ", expected ID_KEYWORD");
        }
    }
}