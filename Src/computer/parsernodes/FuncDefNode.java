package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

import java.lang.Exception;

public class FuncDefNode implements JottTree {
    IDNode funcName;
    ArrayList<FuncDefParamsNode> params;
    TypeNode returnType;
    FuncBodyNode body;

    public FuncDefNode(IDNode name, ArrayList<FuncDefParamsNode> params,
            TypeNode returnType, FuncBodyNode body) {
        this.funcName = name;
        this.params = params;
        this.returnType = returnType;
        this.body = body;
    }

    public static FuncDefNode parse(ArrayList<Token> tokens) throws Exception {

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

        ArrayList<FuncDefParamsNode> params = FuncDefParamsNode.parse(tokens);

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

        TypeNode returnType = TypeNode.parse(tokens);

        if (!tokens.get(0).getToken().equals("{")) 
        {
            throw new Exception();
        }

        FuncBodyNode bodyNode = FuncBodyNode.parse(tokens);

        if (!tokens.get(0).getToken().equals("}")) 
        {
            throw new Exception();
        }

        return new FuncDefNode(name, params, returnType, bodyNode);
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