package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import computer.parsernodes.ExprNode

public class BoolNode implements ExprNode {
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

    public static BoolNode parse(ArrayList<Token> tokens) {

        return new BoolNode();
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}