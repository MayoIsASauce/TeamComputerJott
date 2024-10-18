package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import computer.OperandNode;
import computer.exceptions.ParseException;

public class NumNode implements OperandNode {
    float floatRepresentation;
    int integerRepresentation;
    boolean isFloating;

    public NumNode(float number) {
        this.floatRepresentation = number;
        this.isFloating = true;
    }

    public NumNode(int number) {
        this.integerRepresentation = number;
        this.isFloating = false;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String convertToJott() {
        if (isFloating)
            return Float.toString(floatRepresentation);
        return Integer.toString(integerRepresentation);
    }

    public static NumNode parse(ArrayList<Token> tokens) throws ParseException {
        parse(tokens, false);
    }

    public static NumNode parse(ArrayList<Token> tokens, boolean isNegative) throws ParseException {
        Token token = tokens.get(0);

        if (token.getTokenType() != TokenType.NUMBER)
            throw new ParseException("Attempt to parse number from non-number token: " + token.getToken());

        int multiplier = isNegative ? -1 : 1;
        // try to parse as float, if not then parse as int, if neither then parseexception
        try {
            float floatrep = Float.parseFloat(token.getToken());
            tokens.remove(0);
            return new NumNode(floatrep * multiplier);
        } catch (NumberFormatException e) {
            try {
                int intrep = Integer.parseInt(token.getToken());
                tokens.remove(0);
                return new NumNode(intrep * multiplier);
            } catch (NumberFormatException eInner) {
                throw new ParseException("Invalid number token: " + token.getToken());
            }
        }
        // unreachable
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}