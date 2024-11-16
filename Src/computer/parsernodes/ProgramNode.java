package computer.parsernodes;

import java.util.ArrayList;

import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;
import provided.JottTree;
import provided.Token;

public class ProgramNode implements JottTree
{
    ArrayList<FuncDefNode> fDefNodes;
    Token lineSave;

    public ProgramNode(ArrayList<FuncDefNode> nodes, Token lineSave)
    {
        this.fDefNodes = nodes;
        this.lineSave = lineSave;
    }

    public static ProgramNode parse(ArrayList<Token> tokens) throws ParseException
    {
        ArrayList<FuncDefNode> nodes = new ArrayList<>();
        Token lineSave = null;

        if (tokens.isEmpty())
        {
            lineSave = new Token(null, "NOFILE", 0, null);
        }

        while (!tokens.isEmpty())
        {
            FuncDefNode node = FuncDefNode.parse(tokens);
            nodes.add(node);

            lineSave = new Token(null, node.funcName.getToken().getFilename(), 0, null);
        }

        return new ProgramNode(nodes, lineSave);
    }


    @Override
    public boolean validateTree() throws SemanticException
    {
        boolean mainExists = false;

        for (FuncDefNode fd : fDefNodes)
        {
            fd.validateTree();

            if (fd.getFuncName().equals("main"))
            {
                mainExists = true;
            }
        }

        if (!mainExists)
        {
            throw new SemanticException("No main function found."
                , lineSave);
        }
        
        return true;
    }


    @Override
    public String convertToJott()
    {
        String result = "";

        for (FuncDefNode funcDefNode : fDefNodes)
        {
            result += funcDefNode.convertToJott();
        }

        return result;
    }

    @Override
    public void execute()
    {
        // TODO Auto-generated method stub
        
    }
}