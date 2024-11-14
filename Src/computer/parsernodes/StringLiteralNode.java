package computer.parsernodes;

import java.util.ArrayList;

import provided.Token;
import provided.TokenType;
import computer.exceptions.ParseException;

public class StringLiteralNode implements ExprNode {
    
    String contents;

    public StringLiteralNode(String string) {
        this.contents = string;
    }

    @Override
    public boolean validateTree() { return true; }

    @Override
    public Types getDataType() { return Types.STRING; }

    @Override
    public String convertToJott() {
        return String.format("\"%s\"", contents);
    }

    public static StringLiteralNode parse(ArrayList<Token> tokens) throws ParseException {
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