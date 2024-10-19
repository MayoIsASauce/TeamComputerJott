package computer.parsernodes;

import java.util.ArrayList;

import computer.exceptions.ParseException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public interface BodyStatementNode extends JottTree
{
    @Override
    public boolean validateTree();

    @Override
    public String convertToJott();


    public static BodyStatementNode parse(ArrayList<Token> tokens) throws ParseException
    {
        Token token = tokens.get(0);
        if (token.getTokenType() == TokenType.FC_HEADER)
        {
            // Func_call
            FuncCallNode node = FuncCallNode.parse(tokens);
            node.setInBody();

            if (tokens.get(0).getTokenType() != TokenType.SEMICOLON)
            {
                throw new ParseException("Function call must end with ';'");
            }

            tokens.remove(0);

            return node;
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
            throw new ParseException("Invalid token for body statement");
        }
    }

    @Override
    public void execute();
}