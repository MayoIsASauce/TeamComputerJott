package computer.parsernodes;

import java.util.ArrayList;

import provided.Token;
import provided.TokenType;
import computer.exceptions.ParseException;

public class FuncCallNode implements OperandNode, BodyStatementNode {

    IDNode funcName;
    ParamsNode params;

    public FuncCallNode(IDNode funcName, ParamsNode params)
    {
        this.funcName = funcName;
        this.params = params;
    }

    @Override
    public boolean validateTree()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String convertToJott()
    {
        String result = "::" + funcName.toString() + "[" + params.toString() + "]";

        return result;
    }

    public static FuncCallNode parse(ArrayList<Token> tokens) throws ParseException
    {
        if (tokens.get(0).getTokenType() != TokenType.FC_HEADER)
        {
            throw new ParseException("Function call node must start with \"::\"");
        }

        tokens.remove(0);

        IDNode funcName = IDNode.parse(tokens);

        // "["
        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET)
        {
            throw new ParseException("'[' missing after function name in function call");
        }

        tokens.remove(0);

        ParamsNode params = ParamsNode.parse(tokens);

        // "]"
        if (tokens.get(0).getTokenType() != TokenType.R_BRACKET)
        {
            throw new ParseException("']' missing after parameters in function call");
        }

        tokens.remove(0);

        return new FuncCallNode(funcName, params);
    }

    @Override
    public void execute()
    {
        // TODO Auto-generated method stub

    }
}