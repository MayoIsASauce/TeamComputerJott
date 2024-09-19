package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

import java.lang.Exception;

public class FuncDefNode implements JottTree
{
    IdNode funcName;
    ArrayList<FuncDefParam> params;
    TypeNode returnType;
    FuncBodyNode body;


    public FuncDefNode(IdNode name, ArrayList<FuncDefParam> params,
                        TypeNode returnType, FuncBodyNode body)
    {
        this.funcName = name;
        this.params = params;
        this.returnType = returnType;
        this.body = body;
    }

    public static FuncDefNode parse(ArrayList<Token> tokens) throws Exception
    {
        
        if ( !tokens.get(0).getToken().equals("Def") )
        {
            throw new Exception();
        }

        tokens.remove(0);

        IdNode name = IdNode.parse(tokens);

        if (!tokens.get(0).getToken().equals("["))
        {
            throw new Exception();
        }

        tokens.remove(0);

        ArrayList<FuncDefParam> params = FuncDefParam.parse(tokens);

        return new FuncDefNode(name, params, null, null);
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void execute()
    {
        // TODO Auto-generated method stub
        
    }
}
