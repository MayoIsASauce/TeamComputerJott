package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import computer.parsernodes.BinaryExprNode;
import computer.parsernodes.StringLiteralNode;
import computer.parsernodes.BoolNode;

public interface ExprNode extends JottTree {
    public static ExprNode parse(ArrayList<Token> tokens) {
        Token token = tokens.get(0);

        // first set for boolean
        if (token.getTokenType() == TokenType.ID_KEYWORD && (token.getToken().equals("True") || token.getToken().equals("False"))) {
            return BoolNode.parse(tokens);
        }

        // first set for string literal
        if (token.getTokenType() == TokenType.STRING) {
            return StringLiteralNode.parse(tokens);
        }

        // handles first set for the remaining (all of them start with operand)
        return BinaryExprNode.parse(tokens);
    }
}