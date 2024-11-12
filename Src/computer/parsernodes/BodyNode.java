package computer.parsernodes;

import computer.exceptions.ParseException;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class BodyNode implements JottTree {


    ArrayList<BodyStatementNode> bodyStatements;
    ReturnStatementNode returnStatement;


    public BodyNode(ArrayList<BodyStatementNode> bodyStatements, ReturnStatementNode returnStatement) {
        this.bodyStatements = bodyStatements;
        this.returnStatement = returnStatement;
    }

    public Types getReturnType() {
        return returnStatement.getDataType();
    }

    @Override
    public boolean validateTree() {
        for (BodyStatementNode bodyStatementNode : bodyStatements) {
            if(!bodyStatementNode.validateTree()) {
                return false;
            }
        }

        return returnStatement.validateTree();

    }

    @Override
    public String convertToJott() {
        
        String toReturn = "";
        
        for (int ii = 0; ii < bodyStatements.size(); ii++) {
            toReturn += bodyStatements.get(ii).convertToJott();
        }

        toReturn += returnStatement.convertToJott();
        toReturn += "";

        return toReturn;
    }

    public static BodyNode parse(ArrayList<Token> tokens) throws ParseException
    {

        ArrayList<BodyStatementNode> bodyStatements = new ArrayList<>();
        ReturnStatementNode returnStatement = new ReturnStatementNode(null);

        while (!tokens.get(0).getToken().equals("Return")
                && tokens.get(0).getTokenType() != TokenType.R_BRACE )
        {
            bodyStatements.add(BodyStatementNode.parse(tokens));
        } 

        returnStatement = ReturnStatementNode.parse(tokens);

        return new BodyNode(bodyStatements, returnStatement);

    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}