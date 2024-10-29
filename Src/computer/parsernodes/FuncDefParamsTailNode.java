package computer.parsernodes;

import java.util.ArrayList;

import computer.exceptions.ParseException;
import computer.parsernodes.Types;
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

    public Types type() {
        return type.type(); // convert TypeNode to its inner Types value
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
        String result = "," + id.convertToJott() +  ":" + type.convertToJott();
        return result;
    }

    public static FuncDefParamsTailNode parse(ArrayList<Token> tokens) throws ParseException
    {
        if (!tokens.get(0).getToken().equals(","))
        {
            throw new ParseException("',' missing before parameter");
        }

        tokens.remove(0);

        IDNode id = IDNode.parse(tokens);

        if (!tokens.get(0).getToken().equals(":"))
        {
            throw new ParseException("':' missing after parameter name");
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