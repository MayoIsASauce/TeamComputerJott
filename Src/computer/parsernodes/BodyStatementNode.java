package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public interface BodyStatementNode extends JottTree
{
    @Override
    public boolean validateTree();

    @Override
    public String convertToJott();


    public static BodyStatementNode parse(ArrayList<Token> tokens) throws Exception
    {
        Token token = tokens.get(0);
        if (token.getTokenType() == TokenType.FC_HEADER)
        {
            // Func_call
            return FuncCallNode.parse(tokens);
        }
        else if (token.getTokenType() == TokenType.ID_KEYWORD &&
                token.getToken().equals("If"))
        {
            // If statement
            return IfStatementNode.parse(tokens);
        }
        else if (token.getTokenType() == TokenType.ID_KEYWORD &&
                token.getToken().equals("While"))
        {
            // While loop
            return WhileLoopNode.parse(tokens);
        }
        else if (token.getTokenType() == TokenType.ID_KEYWORD)
        {
            // Asmt
            return AssignmentNode.parse(tokens);
        }
        else
        {
            throw new Exception("Invalid token for body statement");
        }
    }

    @Override
    public void execute();
}