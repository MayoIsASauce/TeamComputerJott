package computer.parsernodes;

import computer.exceptions.ParseException;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;

public class BodyNode implements JottTree {


    ArrayList<BodyStatementNode> bodyStatements;
    ReturnStatementNode returnStatement;


    public BodyNode(ArrayList<BodyStatementNode> bodyStatements, ReturnStatementNode returnStatement) {
        this.bodyStatements = bodyStatements;
        this.returnStatement = returnStatement;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String convertToJott() {
        
        String toReturn = "";
        while(!bodyStatements.isEmpty()) {
            //Once again depends on the handling of the open brace whether its held in function definition or not
            toReturn = "{";
            toReturn += bodyStatements.get(0).convertToJott();
            bodyStatements.remove(0);
            toReturn += returnStatement.convertToJott();
            toReturn += "}";
        }
        return toReturn;
    }

    public static BodyNode parse(ArrayList<Token> tokens) throws ParseException{

        //Slight concern based on whether or not previous function pops out the opening brace

        ArrayList<BodyStatementNode> bodyStatements = new ArrayList<>();
        ReturnStatementNode returnStatement = new ReturnStatementNode(null);

        while(!tokens.get(0).getToken().equals("rbrace")) {

            if(tokens.get(0).getToken().equals("Return")) {
              returnStatement = ReturnStatementNode.parse(tokens);
              tokens.remove(0);
            } 
            bodyStatements.add(BodyStatementNode.parse(tokens));
            tokens.remove(0);
        }

        if(!tokens.get(0).getToken().equals("rbrace")) {
            throw new ParseException("No } found");
        }

        if(tokens.get(0).getToken().equals("rbrace")) {
            tokens.remove(0);
        }

        return new BodyNode(bodyStatements, returnStatement);

    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}