package computer.parsernodes;

import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;
import computer.exceptions.RuntimeException;
import computer.parsernodes.Types;
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

    public boolean isReturnable(Types returnType) {
        for (BodyStatementNode bs : bodyStatements)
            if (bs.isReturnable(returnType))
                return true;
        return returnStatement.getDataType() == returnType;
    }

    @Override
    public boolean validateTree() throws SemanticException {
        for (BodyStatementNode bodyStatementNode : bodyStatements) {
            bodyStatementNode.validateTree();
        }
        
        returnStatement.validateTree();

        return true;

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

        while (!tokens.get(0).getToken().equals("Return")
                && tokens.get(0).getTokenType() != TokenType.R_BRACE )
        {
            bodyStatements.add(BodyStatementNode.parse(tokens));
        } 

        ReturnStatementNode returnStatement = ReturnStatementNode.parse(tokens);

        return new BodyNode(bodyStatements, returnStatement);

    }

    @Override
    public void execute() throws RuntimeException {
        for (BodyStatementNode bs : bodyStatements) {
            bs.execute();
        }
        returnStatement.execute();
    }
}