package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import computer.parsernodes.RelOpType;
import computer.exceptions.ParseException;

public class RelOpNode implements JottTree {

    RelOpType type;
    Token tokenRepresentation;

    public RelOpNode(RelOpType type, Token token) {
        this.type = type;
        this.tokenRepresentation = token;
    }

    Token getToken() { return tokenRepresentation; }

    RelOpType type() { return type; }

    @Override
    public boolean validateTree() { return true; }

    @Override
    public String convertToJott() {
        switch (type) {
            case LESS_THAN:
                return "<";
            case EQ:
                return "=";
            case GREATER_THAN:
                return ">";
            case LESS_THAN_EQ:
                return "<=";
            case GREATER_THAN_EQ:
                return ">=";
            default:
                return "!=";
        }
    }

    public static RelOpNode parse(ArrayList<Token> tokens) throws ParseException {
        Token token = tokens.get(0);

        if (token.getTokenType() != TokenType.REL_OP)
            throw new ParseException("Attempt to parse relop from non-relop token: " + token.getToken());

        RelOpType type;

        switch (token.getToken()) {
            case "<":
                type = RelOpType.LESS_THAN;
                break;
            case "==":
                type = RelOpType.EQ;
                break;
            case ">":
                type = RelOpType.GREATER_THAN;
                break;
            case "<=":
                type = RelOpType.LESS_THAN_EQ;
                break;
            case ">=":
                type = RelOpType.GREATER_THAN_EQ;
                break;
            case "!=":
                type = RelOpType.NOT_EQ;
                break;
            default:
                throw new ParseException("Unknown/invalid relop token: " + token.getToken());
        }

        tokens.remove(0);

        return new RelOpNode(type, token);
    }

    @Override
    public void execute() throws RuntimeException {
        // TODO Auto-generated method stub

    }
}