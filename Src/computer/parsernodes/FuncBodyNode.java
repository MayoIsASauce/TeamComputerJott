package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FuncBodyNode implements JottTree {

    ArrayList<JottTree> varDecList;
    BodyNode body;

    public FuncBodyNode(ArrayList<JottTree> varDecList, BodyNode body) {
        this.varDecList = varDecList;
        this.body = body;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String convertToJott() {

        String toReturn = "";
        while(varDecList.size() > 0) {
            toReturn += varDecList.get(0).convertToJott();
            varDecList.remove(0);
        }

        toReturn += body.convertToJott();
        return toReturn;

    }

    public static FuncBodyNode parse(ArrayList<Token> tokens) {

        ArrayList<JottTree> varDecList = new ArrayList<JottTree>();

        while(tokens.get(0).getToken().equals("Double") || tokens.get(0).getToken().equals("Integer") || tokens.get(0).getToken().equals("String") || tokens.get(0).getToken().equals("Boolean")) {

            varDecList.add(VarDecNode.parse(tokens));
            tokens.remove(0)
            
        }
        
        BodyNode body = BodyNode.parse(tokens);

        return new FuncBodyNode(varDecList, body);

    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}