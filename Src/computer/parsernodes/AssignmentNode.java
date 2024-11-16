package computer.parsernodes;

import java.util.ArrayList;

import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;
import provided.Token;
import provided.TokenType;

public class AssignmentNode implements BodyStatementNode {
    IDNode id;
    ExprNode expr;

    public AssignmentNode(IDNode id, ExprNode expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public boolean isReturnable(Types returnType) { return false; }

    @Override
    public boolean validateTree() throws SemanticException {
        id.validateTree();
        expr.validateTree();
        if (id.getDataType() != expr.getDataType()) {
            throw new SemanticException("Attempt to assign expression of type "
                    + expr.getDataType() + " to variable of type "
                    + id.getDataType(), id.getToken());
        }
        return true;
    }

    @Override
    public String convertToJott() {
        return String.format("%s=%s;", id.convertToJott(), expr.convertToJott());
    }

    public static AssignmentNode parse(ArrayList<Token> tokens) throws ParseException{
        IDNode nId;
        ExprNode nExpr;

        nId = IDNode.parse(tokens);

        if (tokens.get(0).getTokenType() != TokenType.ASSIGN) {
            throw new ParseException("Missing '=' in AssignmentNode");
        }
        tokens.remove(0); // eat assignment = sign

        nExpr = ExprNode.parse(tokens);

        if (tokens.get(0).getTokenType() == TokenType.SEMICOLON) {
            tokens.remove(0);
            return new AssignmentNode(nId, nExpr);
        } else {
            throw new ParseException("Missing semicolon at the end of assignment");
        }
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}