package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import computer.ExprNode;
import computer.exceptions.ParseException;

public class StringLiteralNode implements ExprNode {
    
    String contents;

    public StringLiteralNode(String string) {
        this.contents = string;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String convertToJott() {
        return String.format("\"%s\"", contents);
    }

    public static StringLiteralNode parse(ArrayList<Token> tokens) {
        Token token = tokens.get(0);

        if (token.getTokenType() != TokenType.STRING)
            throw new ParseException("Attempted to parse string literal from non-string: " + token.getToken());

        String string = token.getToken();

        // chop off quotes on either end
        assert string.length() >= 2;
        string = string.substring(1, string.length() - 1);
        tokens.remove(0);

        return new StringLiteralNode(string);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}