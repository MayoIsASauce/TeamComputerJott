package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class ProgramNode implements JottTree
{
    ArrayList<FuncDefNode> fDefNodes;

    public ProgramNode(ArrayList<FuncDefNode> nodes)
    {
        this.fDefNodes = nodes;
    }

    public static ProgramNode parse(ArrayList<Token> tokens)
    {
        ArrayList<FuncDefNode> nodes = new ArrayList<>();

        while (!tokens.isEmpty())
        {
            nodes.add(FuncDefNode.parse(tokens));
        }

        return new ProgramNode(nodes);
    }


    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        
    }
}