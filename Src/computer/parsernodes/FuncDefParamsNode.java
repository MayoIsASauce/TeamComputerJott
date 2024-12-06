package computer.parsernodes;

import java.util.ArrayList;

import computer.SymbolTable;
import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;
import computer.parsernodes.Types;
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

    /// Convert the nodes of the tree into an easy array of the Types enum for
    /// comparison, for example when checking if a function was called with the
    /// correct arguments.
    public ArrayList<Types> typesRepresentation() {
        ArrayList<Types> types = new ArrayList<Types>();
        if (this.paramType != null) {
            types.add(paramType.type());
            for (FuncDefParamsTailNode otherParam : paramsTailArray) {
                types.add(otherParam.type());
            }
        }
        return types;
    }

    public FuncDefParamsNode()
    {
        this.paramName = null;
        this.paramType = null;
        this.paramsTailArray = null;
    }


    @Override
    public boolean validateTree() throws SemanticException
    {
        if (paramName != null)
        {
            paramName.validateTree();
            paramType.validateTree();
        }

        if (paramsTailArray != null)
        {
            for (FuncDefParamsTailNode tailNode : paramsTailArray)
            {
                tailNode.validateTree();
            }
        }

        return true;
    }

    @Override
    public String convertToJott()
    {
        if (this.paramName == null)
        {
            return "";
        }

        String result = paramName.convertToJott() + ":" + paramType.convertToJott();

        for (FuncDefParamsTailNode tailNode : paramsTailArray)
        {
            result += tailNode.convertToJott();
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

        return new FuncDefParamsNode(paramName, paramType, paramsTailArray);
    }

    @Override
    public void execute(Object outparam) {
        // TODO Auto-generated method stub

    }

    public void addToSymbolTable()
    {
        if (paramName != null)
        {
            SymbolTable.instance().addVariableToCurrentScope(paramName.id(), paramType.type());
            SymbolTable.instance().markVariableInitialized(paramName.id());
        }

        if (paramsTailArray != null)
        {
            for (FuncDefParamsTailNode tailNode : paramsTailArray)
            {
                SymbolTable.instance().addVariableToCurrentScope(tailNode.id(), tailNode.type());
                SymbolTable.instance().markVariableInitialized(tailNode.id());
            }
        }
    }
}