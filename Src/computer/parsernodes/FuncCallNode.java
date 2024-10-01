package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import java.lang.Exception;

public class FuncCallNode implements JottTree {

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

    public static FuncCallNode parse(ArrayList<Token> tokens) throws Exception
    {
        if (!tokens.get(0).getToken().equals(":") || !tokens.get(1).getToken().equals(":"))
        {
            throw new Exception("Function call node must start with \"::\"");
        }

        tokens.remove(0);
        tokens.remove(0);

        IDNode funcName = IDNode.parse(tokens);

        // "["
        if (!tokens.get(0).getToken().equals("["))
        {
            throw new Exception();
        }

        tokens.remove(0);

        ParamsNode params = ParamsNode.parse(tokens);

        // "]"
        if (!tokens.get(0).getToken().equals("]"))
        {
            throw new Exception();
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