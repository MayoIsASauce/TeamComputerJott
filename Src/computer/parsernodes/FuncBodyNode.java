package computer.parsernodes;

import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;

public class FuncBodyNode implements JottTree {

    ArrayList<JottTree> varDecList;
    BodyNode body;

    public FuncBodyNode(ArrayList<JottTree> varDecList, BodyNode body) {
        this.varDecList = varDecList;
        this.body = body;
    }

    @Override
    public boolean validateTree() throws SemanticException {
        for (JottTree jottTree : varDecList) {
            jottTree.validateTree();
        }

        body.validateTree();
        

        return true;
    }

    

    @Override
    public String convertToJott() {

        String toReturn = "";
        while(!varDecList.isEmpty()) {
            toReturn += varDecList.get(0).convertToJott();
            varDecList.remove(0);
        }

        toReturn += body.convertToJott();
        return toReturn;

    }

    public static FuncBodyNode parse(ArrayList<Token> tokens) throws ParseException {

        ArrayList<JottTree> varDecList = new ArrayList<>();

        while(tokens.get(0).getToken().equals("Double")
        || tokens.get(0).getToken().equals("Integer")
        || tokens.get(0).getToken().equals("String")
        || tokens.get(0).getToken().equals("Boolean")) {
            varDecList.add(VarDeclarationNode.parse(tokens));
        }

        BodyNode body = BodyNode.parse(tokens);

        return new FuncBodyNode(varDecList, body);

    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}