package computer.parsernodes;

import java.util.ArrayList;

import provided.Token;
import provided.TokenType;
import computer.exceptions.ParseException;
import computer.parsernodes.ExprNode;
import computer.parsernodes.MathOpNode;
import computer.parsernodes.RelOpNode;
import computer.parsernodes.OperandNode;

class ExprOperandOperationOperandNode implements ExprNode {
    MathOpNode mathOp;
    RelOpNode relOp;
    OperandNode lhs;
    OperandNode rhs;

    ExprOperandOperationOperandNode(OperandNode lhs, RelOpNode middle, OperandNode rhs)
    {
        this.lhs = lhs;
        this.relOp = middle;
        this.rhs = rhs;
    }

    ExprOperandOperationOperandNode(OperandNode lhs, MathOpNode middle, OperandNode rhs)
    {
        this.lhs = lhs;
        this.mathOp = middle;
        this.rhs = rhs;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String convertToJott() {
        String middle = String.format(" %s ", mathOp == null ? relOp.convertToJott() : mathOp.convertToJott());
        return lhs.convertToJott() + middle + rhs.convertToJott();
    }

    /// Returns either a ExprOperandOperationOperandNode or an OperandNode. The
    /// latter only happens if the former is not possible.
    public static ExprNode parse(ArrayList<Token> tokens) {
        // in all cases, we have an operand first
        OperandNode first = OperandNode.parse(tokens);

        TokenType nextType = tokens.get(0).getTokenType();

        if (nextType == TokenType.REL_OP)
        {
            RelOpNode middle = RelOpNode.parse(tokens);
            OperandNode second = OperandNode.parse(tokens);
            return new ExprOperandOperationOperandNode(first, middle, second);
        }
        else if (nextType == TokenType.MATH_OP)
        {
            MathOpNode middle = MathOpNode.parse(tokens);
            OperandNode second = OperandNode.parse(tokens);
            return new ExprOperandOperationOperandNode(first, middle, second);
        }

        // no expression parse was possible, this is just a lone operand
        return first;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
    }
}