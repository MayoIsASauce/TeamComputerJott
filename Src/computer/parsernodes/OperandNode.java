package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import computer.exceptions.ParseException;
import computer.parsernodes.IDNode;
import computer.parsernodes.NumNode;
import computer.parsernodes.FuncCallNode;
import computer.parsernodes.ExprNode;

public interface OperandNode extends ExprNode {
    public static OperandNode parse(ArrayList<Token> tokens) throws ParseException {
        Token token = tokens.get(0);

        // lookahead
        if (token.getTokenType() == TokenType.ID_KEYWORD) {
            return IDNode.parse(tokens);
        } else if (token.getTokenType() == TokenType.COLON) {
            // NOTE: assuming func call based on colon could be odd for user if
            // func call is invalid, because the thrown exception will be
            // related to an invalid func call node and nothing about this
            // operand
            return FuncCallNode.parse(tokens);
        }
        else if (token.getTokenType() == TokenType.NUMBER) {
            return NumNode.parse(tokens);
        } else if (
                token.getTokenType() == TokenType.MATH_OP
                && token.getToken().equals("-")
                && tokens.size() > 1
                && tokens.get(1).getTokenType() == TokenType.NUMBER)
        {
            // pop the negative sign, parse the number that comes after
            tokens.remove(0);
            return NumNode.parse(tokens, true);
        }

        throw new ParseException("Attempt to parse token which cannot possible be operand as operand: " + token.getToken());
    }
}