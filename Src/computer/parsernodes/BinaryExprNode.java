package computer.parsernodes;

import java.util.ArrayList;

import provided.Token;
import provided.TokenType;
import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;
import computer.parsernodes.ExprNode;
import computer.parsernodes.MathOpNode;
import computer.parsernodes.RelOpNode;
import computer.parsernodes.OperandNode;

public class BinaryExprNode implements ExprNode {
    MathOpNode mathOp;
    RelOpNode relOp;
    OperandNode lhs;
    OperandNode rhs;

    BinaryExprNode(OperandNode lhs, RelOpNode middle, OperandNode rhs)
    {
        this.lhs = lhs;
        this.relOp = middle;
        this.rhs = rhs;
    }

    BinaryExprNode(OperandNode lhs, MathOpNode middle, OperandNode rhs)
    {
        this.lhs = lhs;
        this.mathOp = middle;
        this.rhs = rhs;
    }

    @Override
    public boolean validateTree() throws SemanticException {
        // ops should both just return true for validate, but just in case
        if (mathOp != null)
            mathOp.validateTree();
        else
            relOp.validateTree();

        lhs.validateTree();
        rhs.validateTree();

        // only if everything is valid, try to get and compare data types
        if (lhs.getDataType() != rhs.getDataType()) {
            Token token = relOp.getToken();
            throw new SemanticException("Mismatched data types on either side of operator " + token.getToken(), token);
        }

        // unreachable due to exceptions being thrown by validates
        return false;
    }

    @Override
    public Types getDataType() {
        // it doesnt really make sense to call this function if the types are
        // invalid. tbh this could even be an "assert validateTree()" but i dont
        // want to make debug builds too slow - Ian
        assert lhs.getDataType() == rhs.getDataType();
        // TODO: assert that the user isnt trying to do < or > on bools / strings?
        // also that they arent trying to do / or * on strings and bools?

        if (relOp != null)
            return Types.BOOLEAN;
        return lhs.getDataType();
    }

    @Override
    public void execute(Object outparam) {
        Object leftObj;
        lhs.execute(leftObj);
        Object rightObj;
        rhs.execute(rightObj);

        if (outparam == null)
            return;

        if (relOp != null) {
            switch (lhs.getDataType()) {
                case Types.DOUBLE: {
                    double left = (double)leftObj;
                    double right = (double)rightObj;
                    switch (relOp.type()) {
                        case RelOpType.EQ: {
                            outparam = left == right;
                            return;
                        }
                        case RelOpType.NOT_EQ: {
                            outparam = left != right;
                            return;
                        }
                        case RelOpType.LESS_THAN: {
                            outparam = left < right;
                            return;
                        }
                        case RelOpType.LESS_THAN_EQ: {
                            outparam = left <= right;
                            return;
                        }
                        case RelOpType.GREATER_THAN: {
                            outparam = left > right;
                            return;
                        }
                        case RelOpType.GREATER_THAN_EQ: {
                            outparam = left >= right;
                            return;
                        }
                    }
                }
                case Types.INTEGER: {
                    int left = (int)leftObj;
                    int right = (int)rightObj;
                    switch (relOp.type()) {
                        case RelOpType.EQ: {
                            outparam = left == right;
                            return;
                        }
                        case RelOpType.NOT_EQ: {
                            outparam = left != right;
                            return;
                        }
                        case RelOpType.LESS_THAN: {
                            outparam = left < right;
                            return;
                        }
                        case RelOpType.LESS_THAN_EQ: {
                            outparam = left <= right;
                            return;
                        }
                        case RelOpType.GREATER_THAN: {
                            outparam = left > right;
                            return;
                        }
                        case RelOpType.GREATER_THAN_EQ: {
                            outparam = left >= right;
                            return;
                        }
                    }
                }

                default: {
                    // TODO: error
                }
            }
        } else {
            switch (lhs.getDataType()) {
                case Types.DOUBLE: {
                    double left = (double)leftObj;
                    double right = (double)rightObj;
                    switch (mathOp.type()) {
                        case MathOpType.MULTIPLY: {
                            outparam = left * right;
                            return;
                        }
                        case MathOpType.DIVIDE: {
                            outparam = left / right;
                            return;
                        }
                        case MathOpType.ADD: {
                            outparam = left + right;
                            return;
                        }
                        case MathOpType.SUBTRACT: {
                            outparam = left - right;
                            return;
                        }
                    }
                }
                case Types.INTEGER: {
                    int left = (int)leftObj;
                    int right = (int)rightObj;
                    switch (mathOp.type()) {
                        case MathOpType.MULTIPLY: {
                            outparam = left * right;
                            return;
                        }
                        case MathOpType.DIVIDE: {
                            outparam = left / right;
                            return;
                        }
                        case MathOpType.ADD: {
                            outparam = left + right;
                            return;
                        }
                        case MathOpType.SUBTRACT: {
                            outparam = left - right;
                            return;
                        }
                    }
                }
                default: {
                    // TODO: error
                }
            }
        }
    }

    @Override
    public String convertToJott() {
        String middle = String.format(" %s ", mathOp == null ? relOp.convertToJott() : mathOp.convertToJott());
        return lhs.convertToJott() + middle + rhs.convertToJott();
    }

    /// Returns either a BinaryExprNode or an OperandNode. The
    /// latter only happens if the former is not possible.
    public static ExprNode parse(ArrayList<Token> tokens) throws ParseException {
        // in all cases, we have an operand first
        OperandNode first = OperandNode.parse(tokens);

        TokenType nextType = tokens.get(0).getTokenType();

        if (nextType == TokenType.REL_OP)
        {
            RelOpNode middle = RelOpNode.parse(tokens);
            OperandNode second = OperandNode.parse(tokens);
            return new BinaryExprNode(first, middle, second);
        }
        else if (nextType == TokenType.MATH_OP)
        {
            MathOpNode middle = MathOpNode.parse(tokens);
            OperandNode second = OperandNode.parse(tokens);
            return new BinaryExprNode(first, middle, second);
        }

        // no expression parse was possible, this is just a lone operand
        return first;
    }
}