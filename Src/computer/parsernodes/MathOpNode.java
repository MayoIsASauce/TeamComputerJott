package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import computer.parsernodes.MathOpType;
import computer.exceptions.ParseException;

public class MathOpNode implements JottTree {

    MathOpType type;

    public MathOpNode(MathOpType type) {
        this.type = type;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String convertToJott() {
        switch (type) {
            case MathOpType.ADD:
                return "+";
            case MathOpType.SUBTRACT:
                return "-";
            case MathOpType.MULTIPLY:
                return "*";
            case MathOpType.DIVIDE:
                return "/";
            default:
                throw new Exception("Unimplemented mathop " + type + " convertToJott method");
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

        return new MathOpNode(type);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}