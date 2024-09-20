package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class TypeNode implements JottTree {
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

    public static TypeNode parse(ArrayList<Token> tokens) {

        return new TypeNode();
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}