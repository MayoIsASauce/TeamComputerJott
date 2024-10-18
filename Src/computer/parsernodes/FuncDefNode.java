package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import computer.parsernodes.IDNode;
import computer.parsernodes.FuncDefParamsNode;

import java.lang.Exception;

public class FuncDefNode implements JottTree {
    IDNode funcName;
    FuncDefParamsNode params;
    FuncReturnNode returnNode;
    FuncBodyNode body;

    public FuncDefNode(IDNode name, FuncDefParamsNode params,
            FuncReturnNode returnNode, FuncBodyNode body)
    {
        this.funcName = name;
        this.params = params;
        this.returnNode = returnNode;
        this.body = body;
    }

    public static FuncDefNode parse(ArrayList<Token> tokens) throws Exception
    {

        if (!tokens.get(0).getToken().equals("Def"))
        {
            throw new Exception();
        }

        tokens.remove(0);

        IDNode name = IDNode.parse(tokens);

        if (!tokens.get(0).getToken().equals("["))
        {
            throw new Exception();
        }

        tokens.remove(0);

        FuncDefParamsNode params = FuncDefParamsNode.parse(tokens);

        if (!tokens.get(0).getToken().equals("]"))
        {
            throw new Exception();
        }

        tokens.remove(0);

        if (!tokens.get(0).getToken().equals(":"))
        {
            throw new Exception();
        }

        tokens.remove(0);

        FuncReturnNode returnNode = FuncReturnNode.parse(tokens);

        if (!tokens.get(0).getToken().equals("{")) 
        {
            throw new Exception();
        }

        FuncBodyNode bodyNode = FuncBodyNode.parse(tokens);

        if (!tokens.get(0).getToken().equals("}")) 
        {
            throw new Exception();
        }

        return new FuncDefNode(name, params, returnNode, bodyNode);
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
        String result = "Def " + funcName + "[" + params + "]:" + returnNode + 
            "{" + body + "}";

        return result;
    }

    @Override
    public void execute()
    {
        // TODO Auto-generated method stub

    }
}