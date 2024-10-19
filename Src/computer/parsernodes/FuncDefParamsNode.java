package computer.parsernodes;

import java.util.ArrayList;

import computer.exceptions.ParseException;
import provided.JottTree;
import provided.Token;

// TODO: Change to throw ParseException

public class FuncDefParamsNode implements JottTree {
    IDNode paramName;
    TypeNode paramType;
    ArrayList<FuncDefParamsTailNode> paramsTailArray;

    public FuncDefParamsNode(IDNode paramName, TypeNode paramType,
        ArrayList<FuncDefParamsTailNode> paramsTailArray)
    {
        this.paramName = paramName;
        this.paramType = paramType;
        this.paramsTailArray = paramsTailArray;
    }

    public FuncDefParamsNode()
    {
        this.paramName = null;
        this.paramType = null;
        this.paramsTailArray = null;
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
        if (this.paramName == null)
        {
            return "";
        }

        String result = paramName + ":" + paramType;

        for (FuncDefParamsTailNode tailNode : paramsTailArray)
        {
            result += tailNode;
        }

        return result;
    }

    public static FuncDefParamsNode parse(ArrayList<Token> tokens) throws ParseException
    {
        // Check follow set ("]"), if found, return Epsilon
        // "]"
        if (tokens.get(0).getToken().equals("]"))
        {
            return new FuncDefParamsNode();
        }

        IDNode paramName = IDNode.parse(tokens);
        
        if (!tokens.get(0).getToken().equals(":"))
        {
            throw new ParseException("':' missing after parameter name");
        }

        tokens.remove(0);

        TypeNode paramType = TypeNode.parse(tokens);

        ArrayList<FuncDefParamsTailNode> paramsTailArray = new ArrayList<>();

        while (!tokens.get(0).getToken().equals("]"))
        {
            FuncDefParamsTailNode paramsTail = FuncDefParamsTailNode.parse(tokens);
            paramsTailArray.add(paramsTail);
        }

        tokens.remove(0);

        return new FuncDefParamsNode(paramName, paramType, paramsTailArray);
    }

    @Override
    public void execute()
    {
        // TODO Auto-generated method stub

    }
}