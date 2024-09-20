package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class VarDeclarationNode implements JottTree {
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

    public static VarDeclarationNode parse(ArrayList<Token> tokens) {

        return new VarDeclarationNode();
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}