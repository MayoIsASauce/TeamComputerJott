package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import computer.parsernodes.MathOpType;
import computer.exceptions.ParseException;

public class MathOpNode implements JottTree {

    MathOpType type;
    Token tokenRepresentation;

    public MathOpNode(MathOpType type, Token token) {
        this.tokenRepresentation = token;
        this.type = type;
    }

    Token getToken() { return tokenRepresentation; }

    @Override
    public boolean validateTree() { return true; }

    public MathOpType type() { return type; }

    @Override
    public String convertToJott() {
        switch (type) {
            case ADD:
                return "+";
            case SUBTRACT:
                return "-";
            case MULTIPLY:
                return "*";
            default:
                return "/";
        }
    }

    public static MathOpNode parse(ArrayList<Token> tokens) throws ParseException {
        Token token = tokens.get(0);

        if (token.getTokenType() != TokenType.MATH_OP)
            throw new ParseException("Attempted to parse non mathop token as mathop: " + token.getToken());

        MathOpType type;
        switch (token.getToken()) {
            case "+":
                type = MathOpType.ADD;
                break;
            case "-":
                type = MathOpType.SUBTRACT;
                break;
            case "*":
                type = MathOpType.MULTIPLY;
                break;
            case "/":
                type = MathOpType.DIVIDE;
                break;
            default:
                throw new ParseException("Invalid/unknown mathop token: " + token.getToken());
        }

        tokens.remove(0);

        return new MathOpNode(type, token);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}