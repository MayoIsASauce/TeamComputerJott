package computer.parsernodes;

import java.util.ArrayList;
import java.util.List;

import provided.Token;
import provided.TokenType;
import computer.exceptions.ParseException;

public class FuncCallNode implements OperandNode, BodyStatementNode {

    IDNode funcName;
    ParamsNode params;

    boolean isInBody;

    public FuncCallNode(IDNode funcName, ParamsNode params)
    {
        this.funcName = funcName;
        this.params = params;
        this.isInBody = false;
    }

    public void setInBody()
    {
        isInBody = true;
    }

    @Override
    public boolean validateTree()
    {
        if (!funcName.validateTree() || !params.validateTree())
            return false;

        // child nodes are valid, now make sure that arguments match parameters
        FunctionInfo calledFunction = SymbolTable.instance().functionInfo(funcName.id());
        List<ExprNode> exprs = params.parameters();
        List<Types> calledFunctionTypes = calledFunction.parameterTypes();

        if (exprs.size() != calledFunctionTypes.size())
            // wrong number of function arguments
            return false;

        for (int i = 0; i < exprs.size(); ++i)
        {
            if (exprs.get(i).getDataType() != calledFunctionTypes.get(i))
                // argument of incorrect type provided for arg `i`
                return false;
        }
        return true;
    }

    @Override
    public Types getDataType() {
        return SymbolTable.instance().functionInfo(funcName.id()).returnType();
    }

    @Override
    public String convertToJott()
    {
        String result = "::" + funcName.convertToJott() + "[" + params.convertToJott() + "]";

        if (isInBody)
        {
            result += ";";
        }

        return result;
    }

    public static FuncCallNode parse(ArrayList<Token> tokens) throws ParseException
    {
        if (tokens.get(0).getTokenType() != TokenType.FC_HEADER)
        {
            throw new ParseException("Function call node must start with \"::\"");
        }

        tokens.remove(0);

        IDNode funcName = IDNode.parse(tokens);

        // "["
        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET)
        {
            throw new ParseException("'[' missing after function name in function call");
        }

        tokens.remove(0);

        ParamsNode params = ParamsNode.parse(tokens);

        // "]"
        if (tokens.get(0).getTokenType() != TokenType.R_BRACKET)
        {
            throw new ParseException("']' missing after parameters in function call");
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