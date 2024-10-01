package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import computer.parsernodes.IDNode;
import computer.parsernodes.NumNode;
import computer.parsernodes.FuncCallNode;

public class OperandNode implements JottTree {

    IDNode variantIDNode;
    NumNode variantNumNode;
    FuncCallNode variantFuncCallNode;
    boolean isNegative;

    public OperandNode(IDNode id) {
        this.variantIDNode = id;
    }

    public OperandNode(NumNode num, boolean negative) {
        this.variantNumNode = num;
        this.isNegative = negative;
    }

    public OperandNode(FuncCallNode call) {
        this.variantFuncCallNode = call;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        return null;
    }

    public static OperandNode parse(ArrayList<Token> tokens) {
        Token token = tokens.get(0);

        // lookahead
        if (token.getTokenType() == TokenType.ID_KEYWORD) {
            return new OperandNode(IDNode.parse(tokens));
        } else if (token.getTokenType() == TokenType.COLON) {
            // NOTE: assuming func call based on colon could be odd for user if
            // func call is invalid, because the thrown exception will be
            // related to an invalid func call node and nothing about this
            // operand
            return new OperandNode(FuncCallNode.parse(tokens));
        }
        else if (token.getTokenType() == TokenType.NUMBER) {
            return new OperandNode(NumNode.parse(tokens),  false);
        } else if (
                token.getTokenType() == TokenType.MATH_OP
                && token.getToken().equals("-")
                && tokens.size() > 1
                && tokens.get(1).getTokenType() == TokenType.NUMBER)
        {
            // pop the negative sign, parse the number that comes after
            tokens.remove(0);
            return new OperandNode(NumNode.parse(tokens), true);
        }

        throw new ParseException("Attempt to parse token which cannot possible be operand as operand: " + token.getToken());
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}