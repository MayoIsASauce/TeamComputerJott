package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class FuncDefParamsTailNode implements JottTree {
    IDNode id;
    TypeNode type;

    public FuncDefParamsTailNode(IDNode id, TypeNode type)
    {
        this.id = id;
        this.type = type;
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
        String result = "," + id +  ":" + type;
        return result;
    }

    public static FuncDefParamsTailNode parse(ArrayList<Token> tokens) throws Exception
    {
        if (!tokens.get(0).getToken().equals(","))
        {
            throw new Exception();
        }

        tokens.remove(0);

        IDNode id = IDNode.parse(tokens);

        if (!tokens.get(0).getToken().equals(":"))
        {
            throw new Exception();
        }

        tokens.remove(0);

        TypeNode type = TypeNode.parse(tokens);

        return new FuncDefParamsTailNode(id, type);
    }

    @Override
    public void execute()
    {
        // TODO Auto-generated method stub

    }
}