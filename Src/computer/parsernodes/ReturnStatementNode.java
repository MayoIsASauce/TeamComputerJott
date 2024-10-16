package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class ReturnStatementNode implements JottTree {

    ExprNode expr;

    public ReturnStatementNode(ExprNode expr) {
        this.expr = expr;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String convertToJott() {
        String toReturn = "return ";
        toReturn += expr.convertToJott();
        return toReturn;
    }

    public static ReturnStatementNode parse(ArrayList<Token> tokens) {
        
        if(!tokens.get(0).getToken().equals("Return")) {
            throw new Exception();
        }
        
        tokens.remove(0);

        ExprNode expr = ExprNode.parse(tokens);
        
        return new ReturnStatementNode(expr);

    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}