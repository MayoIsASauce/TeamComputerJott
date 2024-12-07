package computer.parsernodes;

import java.util.ArrayList;

import provided.Token;
import provided.TokenType;
import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;
import computer.exceptions.RuntimeException;
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
        if (lhs.getDataType() == Types.VOID || lhs.getDataType() == Types.STRING || lhs.getDataType() == Types.BOOLEAN) {
            return false;
        }
        if (rhs.getDataType() == Types.VOID || rhs.getDataType() == Types.STRING || rhs.getDataType() == Types.BOOLEAN) {
            return false;
        }

        // ops should both just return true for validate, but just in case
        if (mathOp != null)
            mathOp.validateTree();
        else
            relOp.validateTree();

        lhs.validateTree();
        rhs.validateTree();

        // only if everything is valid, try to get and compare data types
        if (lhs.getDataType() != rhs.getDataType()) {
            Token token = relOp != null ? relOp.getToken() : mathOp.getToken();
            throw new SemanticException("Mismatched data types on either side of operator " + token.getToken(), token);
        }

        // unreachable due to exceptions being thrown by validates
        return false;
    }

    @Override
    public Token getToken() { return lhs.getToken(); }

    @Override
    public Types getDataType() {
        // it doesnt really make sense to call this function if the types are
        // invalid. tbh this could even be an "assert validateTree()" but i dont
        // want to make debug builds too slow - Ian
        assert lhs.getDataType() == rhs.getDataType();

        if (relOp != null)
            return Types.BOOLEAN;
        return lhs.getDataType();
    }


    @Override
    public void execute() throws RuntimeException {
        /// dont call this function, exprs should return something
        assert false;
    }

    @Override
    public Object executeAndReturnData() throws RuntimeException {
        Object leftObj = lhs.executeAndReturnData();
        Object rightObj = rhs.executeAndReturnData();

        if (relOp != null) {
            switch (lhs.getDataType()) {
                case DOUBLE: {
                    double left = (double)leftObj;
                    double right = (double)rightObj;
                    switch (relOp.type()) {
                        case EQ: {
                            return left == right;
                        }
                        case NOT_EQ: {
                            return left != right;
                        }
                        case LESS_THAN: {
                            return left < right;
                        }
                        case LESS_THAN_EQ: {
                            return left <= right;
                        }
                        case GREATER_THAN: {
                            return left > right;
                        }
                        case GREATER_THAN_EQ: {
                            return left >= right;
                        }
                    }
                }
                case INTEGER: {
                    int left = (int)leftObj;
                    int right = (int)rightObj;
                    switch (relOp.type()) {
                        case EQ: {
                            return left == right;
                        }
                        case NOT_EQ: {
                            return left != right;
                        }
                        case LESS_THAN: {
                            return left < right;
                        }
                        case LESS_THAN_EQ: {
                            return left <= right;
                        }
                        case GREATER_THAN: {
                            return left > right;
                        }
                        case GREATER_THAN_EQ: {
                            return left >= right;
                        }
                    }
                }

                default: {
                    // should never happen
                    throw new RuntimeException("Cannot compare types of " + lhs.getDataType(), relOp.getToken());
                }
            }
        } else {
            switch (lhs.getDataType()) {
                case DOUBLE: {
                    double left = (double)leftObj;
                    double right = (double)rightObj;
                    switch (mathOp.type()) {
                        case MULTIPLY: {
                            return left * right;
                        }
                        case DIVIDE: {
                            if (right == 0) {
                                throw new RuntimeException("Attempt to floating point divide by zero", mathOp.getToken());
                            }
                            return left / right;
                        }
                        case ADD: {
                            return left + right;
                        }
                        case SUBTRACT: {
                            return left - right;
                        }
                    }
                }
                case INTEGER: {
                    int left = (int)leftObj;
                    int right = (int)rightObj;
                    switch (mathOp.type()) {
                        case MULTIPLY: {
                            return left * right;
                        }
                        case DIVIDE: {
                            if (right == 0) {
                                throw new RuntimeException("Attempt to integer divide by zero", mathOp.getToken());
                            }
                            return left / right;
                        }
                        case ADD: {
                            return left + right;
                        }
                        case SUBTRACT: {
                            return left - right;
                        }
                    }
                }
                default: {
                    // should never happen
                    throw new RuntimeException("Cannot do math on type of " + lhs.getDataType(), mathOp.getToken());
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