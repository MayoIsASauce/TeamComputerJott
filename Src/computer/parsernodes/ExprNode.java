package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import computer.parsernodes.OperandNode;
import computer.parsernodes.RelOpNode;
import computer.parsernodes.MathOpNode;
import computer.parsernodes.StringLiteralNode;
import computer.parsernodes.BoolNode;

public class ExprNode implements JottTree {

    BoolNode bool;
    StringLiteralNode string;
    MathOpNode mathOp;
    RelOpNode relOp;
    OperandNode lhs;
    OperandNode rhs;

    public ExprNode(BoolNode bool) {
        this.bool = bool;
    }

    public ExprNode(StringLiteralNode string) {
        this.string = string;
    }

    public ExprNode(OperandNode operand) {
        this.lhs = operand;
    }

    public ExprNode(OperandNode lhs, MathOpNode mathOp, OperandNode rhs) {
        this.lhs = lhs;
        this.mathOp = mathOp;
        this.rhs = rhs;
    }

    public ExprNode(OperandNode lhs, RelOpNode relOp, OperandNode rhs) {
        this.lhs = lhs;
        this.relOp = relOp;
        this.rhs = rhs;
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

    public static ExprNode parse(ArrayList<Token> tokens) {

        Token token = tokens.get(0);

        try {
            return new ExprNode(BoolNode.parse(tokens));
        } catch (ParseException e) {}

        try {
            return new ExprNode(StringLiteralNode.parse(tokens));
        } catch (ParseException e) {}

        try {
            OperandNode first = OperandNode.parse(tokens);

            MathOpNode maybeMathOp = null;
            RelOpNode maybeRelOp = null;

            try {
                maybeMathOp = MathOpNode.parse(tokens);
            } catch (ParseException e) {
                try {
                    maybeRelOp = RelOpNode.parse(tokens);
                } catch (ParseException eInner) {}
            }

            if (maybeRelOp == null && maybeMathOp == null) {
                return new ExprNode(first);
            }

            // BUG: if this fails, we cannot restore the stack to its original
            // state, meaning that its not valid for other nodes to parse expr
            // in a try block as a form of lookahead (the tokens will be
            // modified)
            // The good news is that if this is invalid, the whole program is
            // invalid anyways :)
            OperandNode second = OperandNode.parse(tokens);

            if (maybeMathOp != null) {
                assert maybeRelOp == null;
                return new ExprNode(first, maybeMathOp, second);
            } else {
                assert maybeRelOp != null;
                return new ExprNode(first, maybeRelOp, second);
            }

        } catch (ParseException e) {
            throw e;
        }
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}